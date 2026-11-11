package it.cocktail.ui.detail.model

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import it.cocktail.core.ui.model.UiError

class DrinkDetailUiStateTest :
    BehaviorSpec({
        Given("a DrinkDetailUiState") {
            When("error is present") {
                val state = DrinkDetailUiState(
                    error = UiError.Generic,
                    isLoading = true,
                    drinkDetail = DrinkDetailUiModel.sample(),
                )

                Then("isError should be true") {
                    state.isError shouldBe true
                }

                Then("contentState should be DetailContentState.Error") {
                    state.contentState shouldBe DetailContentState.Error
                }
            }

            When("isLoading is true and drinkDetail is null") {
                val state = DrinkDetailUiState(
                    isLoading = true,
                    drinkDetail = null,
                )

                Then("contentState should be DetailContentState.Loading") {
                    state.contentState shouldBe DetailContentState.Loading
                }

                Then("isRefreshing should be false") {
                    state.isRefreshing shouldBe false
                }
            }

            When("isLoading is false and drinkDetail is null") {
                val state = DrinkDetailUiState(
                    isLoading = false,
                    drinkDetail = null,
                )

                Then("contentState should be DetailContentState.Loading") {
                    state.contentState shouldBe DetailContentState.Loading
                }
            }

            When("isLoading is true and drinkDetail is present") {
                val state = DrinkDetailUiState(
                    isLoading = true,
                    drinkDetail = DrinkDetailUiModel.sample(),
                )

                Then("contentState should be DetailContentState.Content") {
                    state.contentState shouldBe DetailContentState.Content
                }

                Then("isRefreshing should be true") {
                    state.isRefreshing shouldBe true
                }
            }

            When("isLoading is false and drinkDetail is present") {
                val state = DrinkDetailUiState(
                    isLoading = false,
                    drinkDetail = DrinkDetailUiModel.sample(),
                )

                Then("contentState should be DetailContentState.Content") {
                    state.contentState shouldBe DetailContentState.Content
                }

                Then("isRefreshing should be false") {
                    state.isRefreshing shouldBe false
                }

                Then("isError should be false") {
                    state.isError shouldBe false
                }
            }
        }
    })
