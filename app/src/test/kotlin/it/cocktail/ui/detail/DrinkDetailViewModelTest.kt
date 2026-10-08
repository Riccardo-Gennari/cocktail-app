package it.cocktail.ui.detail

import androidx.compose.material3.SnackbarResult
import app.cash.turbine.test
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import it.cocktail.R
import it.cocktail.core.common.log.Logger
import it.cocktail.core.ui.model.UiText
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

            fun setupGetDetail(): CompletableDeferred<Result<DrinkDetail>> {
                val deferred = CompletableDeferred<Result<DrinkDetail>>()
                coEvery { getDetailUseCase(id = expectedDrink.id) } coAnswers { deferred.await() }
                return deferred
            }

            val failure = Result.failure<DrinkDetail>(IllegalStateException("fetch failed"))

            When("fetching drink detail succeeds") {
                Then("uiState emits loading then success state") {
                    val fetch = setupGetDetail()

                    createViewModel().uiState.test {
                        awaitUntil { it.isLoading }
                        fetch.complete(Result.success(expectedDrink))

                        val state = awaitUntil { !it.isLoading && it.drinkDetail != null }
                        state.drinkDetail shouldBe expectedDrink.toUi()
                    }
                    coVerify(exactly = 1) { getDetailUseCase(id = expectedDrink.id) }
                }
            }

            When("fetching drink detail fails") {
                Then("uiState emits loading then error state") {
                    val fetch = setupGetDetail()

                    createViewModel().uiState.test {
                        awaitUntil { it.isLoading }
                        fetch.complete(failure)

                        val state = awaitUntil { !it.isLoading && it.message != null }
                        state.drinkDetail shouldBe null
                        state.message?.message shouldBe UiText.StringResource(R.string.something_went_wrong)
                    }
                    coVerify(exactly = 1) { getDetailUseCase(id = expectedDrink.id) }
                }

                Then("uiMessage is cleared when snackBarResult is dismissed") {
                    val fetch = setupGetDetail()
                    val viewModel = createViewModel()

                    viewModel.uiState.test {
                        awaitUntil { it.isLoading }
                        fetch.complete(failure)
                        awaitUntil { !it.isLoading && it.message != null }

                        viewModel.onMessageResult(SnackbarResult.Dismissed)
                        awaitUntil { it.message == null }
                    }
                }

                Then("uiMessage is cleared and fetch is retried when snackBarResult is action performed") {
                    val fetch = setupGetDetail()
                    val viewModel = createViewModel()

                    viewModel.uiState.test {
                        awaitUntil { it.isLoading }
                        fetch.complete(failure)
                        awaitUntil { !it.isLoading && it.message != null }

                        val retry = setupGetDetail()
                        viewModel.onMessageResult(SnackbarResult.ActionPerformed)

                        awaitUntil { it.isLoading && it.message == null }
                        retry.complete(Result.success(expectedDrink))

                        val state = awaitUntil { !it.isLoading && it.drinkDetail != null }
                        state.drinkDetail shouldBe expectedDrink.toUi()
                    }
                    coVerify(exactly = 2) { getDetailUseCase(id = expectedDrink.id) }
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
