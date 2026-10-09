package it.cocktail.ui.detail

import app.cash.turbine.test
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import it.cocktail.core.common.log.Logger
import it.cocktail.domain.model.DrinkDetail
import it.cocktail.domain.usecase.GetDrinkDetailUseCase
import it.cocktail.ui.detail.mapper.toUi
import it.cocktail.ui.detail.navigation.DrinkDetailNavigator
import it.cocktail.util.awaitUntil
import kotlinx.coroutines.CompletableDeferred

class DrinkDetailViewModelTest :
    BehaviorSpec({
        Given("a DrinkDetailViewModel") {
            val expectedDrink = DrinkDetail(id = 1, name = "drinkName", category = "drinkCategory")
            val getDetailUseCase = mockk<GetDrinkDetailUseCase>()
            val log = mockk<Logger>(relaxed = true)
            val navigator = mockk<DrinkDetailNavigator>(relaxed = true)

            fun createViewModel() = DrinkDetailViewModel(drinkId = expectedDrink.id, getDetailUseCase, log, navigator)

            fun setupGetDetail(forceRefresh: Boolean = false): CompletableDeferred<Result<DrinkDetail>> {
                val deferred = CompletableDeferred<Result<DrinkDetail>>()
                coEvery {
                    getDetailUseCase(id = expectedDrink.id, forceRefresh = forceRefresh)
                } coAnswers { deferred.await() }
                return deferred
            }

            val failure = Result.failure<DrinkDetail>(IllegalStateException("fetch failed"))

            When("fetching drink detail succeeds") {
                Then("uiState emits loading then success state") {
                    val fetch = setupGetDetail(forceRefresh = false)

                    createViewModel().uiState.test {
                        awaitUntil { it.isLoading }
                        fetch.complete(Result.success(expectedDrink))

                        val state = awaitUntil { !it.isLoading && it.drinkDetail != null }
                        state.drinkDetail shouldBe expectedDrink.toUi()
                    }
                    coVerify(exactly = 1) { getDetailUseCase(id = expectedDrink.id, forceRefresh = false) }
                }
            }

            When("fetching drink detail fails") {
                Then("uiState emits loading then error state") {
                    val fetch = setupGetDetail(forceRefresh = false)

                    createViewModel().uiState.test {
                        awaitUntil { it.isLoading }
                        fetch.complete(failure)

                        val state = awaitUntil { !it.isLoading && it.isError }
                        state.drinkDetail shouldBe null
                        state.isError shouldBe true
                    }
                    coVerify(exactly = 1) { getDetailUseCase(id = expectedDrink.id, forceRefresh = false) }
                }

                Then("error is cleared and fetch is retried with forceRefresh = true on refresh") {
                    val fetch = setupGetDetail(forceRefresh = false)
                    val viewModel = createViewModel()

                    viewModel.uiState.test {
                        awaitUntil { it.isLoading }
                        fetch.complete(failure)
                        awaitUntil { !it.isLoading && it.isError }

                        val retry = setupGetDetail(forceRefresh = true)
                        viewModel.onRefresh()

                        awaitUntil { it.isLoading && !it.isError }
                        retry.complete(Result.success(expectedDrink))

                        val state = awaitUntil { !it.isLoading && it.drinkDetail != null }
                        state.drinkDetail shouldBe expectedDrink.toUi()
                    }
                    coVerify(exactly = 1) { getDetailUseCase(id = expectedDrink.id, forceRefresh = false) }
                    coVerify(exactly = 1) { getDetailUseCase(id = expectedDrink.id, forceRefresh = true) }
                }
            }

            When("navigating back") {
                Then("navigator is invoked") {
                    createViewModel().onNavigateBack()
                    verify(exactly = 1) { navigator.navigateBack() }
                }
            }
        }
    })
