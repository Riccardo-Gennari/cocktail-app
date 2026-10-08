package it.cocktail.ui.detail

import app.cash.turbine.test
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.kotest.matchers.string.beBlank
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import it.cocktail.domain.model.DrinkDetail
import it.cocktail.domain.usecase.GetDrinkDetailUseCase
import it.cocktail.ui.detail.mapper.toUi
import it.cocktail.ui.detail.navigation.DrinkDetailNavigator
import kotlinx.coroutines.CompletableDeferred

class DrinkDetailViewModelTest :
    BehaviorSpec({
        Given("a DrinkDetailViewModel") {
            val expectedDrink = DrinkDetail(id = 1, name = "drinkName", category = "drinkCategory")
            val getDetailUseCase = mockk<GetDrinkDetailUseCase>()
            val navigator = mockk<DrinkDetailNavigator>(relaxed = true)

            coEvery { getDetailUseCase(any()) } returns Result.success(expectedDrink)

            fun createViewModel() = DrinkDetailViewModel(drinkId = expectedDrink.id, getDetailUseCase, navigator)

            When("fetching drink detail succeeds") {
                val fetchDeferred = CompletableDeferred<Result<DrinkDetail>>()
                coEvery { getDetailUseCase(id = expectedDrink.id) } coAnswers { fetchDeferred.await() }

                Then("uiState emits loading then success state") {
                    val viewModel = createViewModel()

                    viewModel.uiState.test {
                        val initialState = awaitItem()
                        initialState.isLoading shouldBe false

                        val loadingState = awaitItem()
                        loadingState.isLoading shouldBe true

                        fetchDeferred.complete(Result.success(expectedDrink))

                        val successState = awaitItem()
                        successState.isLoading shouldBe false
                        successState.drinkDetail shouldBe expectedDrink.toUi()
                    }
                    coVerify(exactly = 1) { getDetailUseCase(id = expectedDrink.id) }
                }
            }

            When("fetching drink detail fails") {
                val fetchDeferred = CompletableDeferred<Result<DrinkDetail>>()
                coEvery { getDetailUseCase(id = expectedDrink.id) } coAnswers { fetchDeferred.await() }

                Then("uiState emits loading then error state, and clears error when message is shown") {
                    val viewModel = createViewModel()

                    viewModel.uiState.test {
                        val initialState = awaitItem()
                        initialState.isLoading shouldBe false

                        val loadingState = awaitItem()
                        loadingState.isLoading shouldBe true

                        val fetchException = IllegalStateException("fetch failed")
                        fetchDeferred.complete(Result.failure(fetchException))

                        val errorState = awaitItem()
                        errorState.isLoading shouldBe false
                        errorState.drinkDetail shouldBe null
                        errorState.message shouldNot beBlank()

                        viewModel.onMessageShown()
                        awaitItem().message shouldBe null
                    }
                    coVerify(exactly = 1) { getDetailUseCase(id = expectedDrink.id) }
                }
            }

            When("navigating back") {
                Then("navigator is invoked") {
                    val viewModel = createViewModel()
                    viewModel.onNavigateBack()
                    verify(exactly = 1) { navigator.navigateBack() }
                }
            }
        }
    })
