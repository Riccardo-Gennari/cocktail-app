package it.cocktail.ui.list.model

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import it.cocktail.core.ui.model.UiError

class DrinkListUiStateTest :
    BehaviorSpec({
        Given("a DrinkListUiState") {
            When("error is present") {
                val state = DrinkListUiState(
                    error = UiError.NoConnection,
                    isLoading = true,
                    drinkList = DrinkSummaryUiModel.sample(2),
                )

                Then("isError should be true") {
                    state.isError shouldBe true
                }

                Then("contentState should be ListContentState.Error") {
                    state.contentState shouldBe ListContentState.Error
                }
            }

            When("isLoading is true and drinkList is empty") {
                val state = DrinkListUiState(
                    isLoading = true,
                    drinkList = emptyList(),
                )

                Then("contentState should be ListContentState.Loading") {
                    state.contentState shouldBe ListContentState.Loading
                }

                Then("isRefreshing should be false") {
                    state.isRefreshing shouldBe false
                }
            }

            When("isLoading is true and drinkList is not empty") {
                val drinks = DrinkSummaryUiModel.sample(3)
                val state = DrinkListUiState(
                    isLoading = true,
                    drinkList = drinks,
                )

                Then("contentState should be ListContentState.Content") {
                    state.contentState shouldBe ListContentState.Content
                }

                Then("isRefreshing should be true") {
                    state.isRefreshing shouldBe true
                }
            }

            When("isLoading is false and drinkList is empty") {
                val state = DrinkListUiState(
                    isLoading = false,
                    drinkList = emptyList(),
                )

                Then("contentState should be ListContentState.Empty") {
                    state.contentState shouldBe ListContentState.Empty
                }

                Then("isRefreshing should be false") {
                    state.isRefreshing shouldBe false
                }
            }

            When("isLoading is false and drinkList is not empty") {
                val drinks = DrinkSummaryUiModel.sample(3)
                val state = DrinkListUiState(
                    isLoading = false,
                    drinkList = drinks,
                )

                Then("contentState should be ListContentState.Content") {
                    state.contentState shouldBe ListContentState.Content
                }

                Then("isRefreshing should be false") {
                    state.isRefreshing shouldBe false
                }
            }
        }
    })
