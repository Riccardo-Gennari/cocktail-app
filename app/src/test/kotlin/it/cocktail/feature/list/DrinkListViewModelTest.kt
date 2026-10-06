package it.cocktail.feature.list

import app.cash.turbine.test
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.mockk.coEvery
import io.mockk.mockk
import it.cocktail.domain.model.DrinkQuery
import it.cocktail.domain.model.DrinkSummary
import it.cocktail.domain.usecase.GetDrinksUseCase
import it.cocktail.feature.list.mapper.toDrinkQuery
import it.cocktail.feature.list.mapper.toUi
import it.cocktail.feature.list.model.SearchState
import it.cocktail.feature.list.model.SearchType

class DrinkListViewModelTest :
    BehaviorSpec({
        Given("an instance") {
            val getDrinks = mockk<GetDrinksUseCase>()
            val viewModel = DrinkListViewModel(getDrinks)

            When("it is initialized") {
                coEvery { getDrinks(any<DrinkQuery>()) } returns Result.success(emptyList())

                Then("it should expose a non-empty uiState flow") {
                    viewModel.uiState.test {
                        shouldNotThrow<AssertionError> { awaitItem() }
                    }
                }
            }

            When("a valid search query is set") {
                withData(
                    SearchState(query = "name", type = SearchType.NAME),
                    SearchState(query = "ingredient", type = SearchType.INGREDIENT),
                ) { searchState ->
                    viewModel.onSearchTypeChanged(searchState.type)
                    viewModel.onSearchQueryChanged(searchState.query)

                    Then("uiState should be updated with the new query") {
                        viewModel.uiState.test {
                            skipItems(1)
                            awaitItem().searchState shouldBe searchState
                        }
                    }

                    And("search is triggered") {
                        And("it succeeds") {
                            val expectedDrinks = List(3) { index ->
                                DrinkSummary(id = index, name = "name $index", imageUri = "image $index")
                            }

                            coEvery { getDrinks(searchState.toDrinkQuery()) } returns Result.success(expectedDrinks)

                            Then("uiState should be updated with the new data") {
                                viewModel.uiState.test {
                                    skipItems(2) // initial emissions
                                    viewModel.onSearch()
                                    awaitItem().isLoading shouldBe true

                                    val successState = awaitItem()
                                    successState.isLoading shouldBe false
                                    successState.drinkList shouldBe expectedDrinks.map { it.toUi() }
                                }
                            }
                        }

                        And("it fails") {
                            val exception = IllegalStateException("exception msg")

                            coEvery { getDrinks(searchState.toDrinkQuery()) } returns Result.failure(exception)

                            Then("uiState should be updated with empty data and error message") {
                                viewModel.uiState.test {
                                    skipItems(2) // initial emissions
                                    viewModel.onSearch()
                                    awaitItem().isLoading shouldBe true

                                    val errorState = awaitItem()
                                    errorState.isLoading shouldBe false
                                    errorState.drinkList.shouldBeEmpty()
                                    errorState.message shouldContain exception.message!!
                                }
                            }
                        }
                    }
                }
            }
        }
    })
