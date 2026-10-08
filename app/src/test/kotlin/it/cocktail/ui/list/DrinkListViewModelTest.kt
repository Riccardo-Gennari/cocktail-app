package it.cocktail.ui.list

import app.cash.turbine.test
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.string.shouldContain
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import it.cocktail.domain.model.DrinkQuery
import it.cocktail.domain.model.DrinkSummary
import it.cocktail.domain.usecase.GetDrinksUseCase
import it.cocktail.ui.list.mapper.toDrinkQuery
import it.cocktail.ui.list.mapper.toUi
import it.cocktail.ui.list.model.DrinkSummaryUiModel
import it.cocktail.ui.list.model.SearchState
import it.cocktail.ui.list.model.SearchType
import it.cocktail.ui.list.navigation.DrinkListNavigator

class DrinkListViewModelTest :
    BehaviorSpec(
        {
            Given("an instance") {
                val getDrinks = mockk<GetDrinksUseCase>()
                val navigator = mockk<DrinkListNavigator>(relaxed = true)
                val viewModel = DrinkListViewModel(getDrinks, navigator)

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

                                        viewModel.onMessageShown()

                                        val clearedState = awaitItem()
                                        clearedState.message shouldBe null
                                    }
                                }
                            }
                        }
                    }
                }

                And("searchType transitions occur") {
                    val initialSearchType = viewModel.uiState.value.searchState.type
                    val transitions = SearchType.entries.flatMap { from ->
                        SearchType.entries.map { to -> from to to }
                    }

                    transitions.forEach { (fromType, toType) ->
                        When("changing searchType from $fromType to $toType") {
                            viewModel.uiState.test {
                                awaitItem() // Consume initial emission

                                viewModel.onSearchTypeChanged(fromType)
                                if (fromType != initialSearchType) awaitItem().searchState.type shouldBe fromType

                                viewModel.onSearchQueryChanged("search text")
                                awaitItem().searchState.query shouldBe "search text"

                                viewModel.onSearchTypeChanged(toType)

                                if (fromType != toType) {
                                    Then("uiState is updated accordingly") {
                                        val updatedState = awaitItem().searchState
                                        updatedState.type shouldBe toType
                                        updatedState.query.shouldBeEmpty()
                                    }
                                } else {
                                    Then("no changes occur") {
                                        expectNoEvents()
                                    }
                                }
                            }
                        }
                    }
                }

                And("searchQuery transitions occur") {
                    val queryPairs = listOf(
                        "" to "new query",
                        "old query" to "new query",
                        "same query" to "same query",
                        "" to "",
                    )

                    queryPairs.forEach { (fromQuery, toQuery) ->
                        When("changing query from '$fromQuery' to '$toQuery'") {
                            viewModel.uiState.test {
                                awaitItem() // Consume initial emission

                                if (fromQuery.isNotEmpty()) {
                                    viewModel.onSearchQueryChanged(fromQuery)
                                    awaitItem().searchState.query shouldBe fromQuery
                                }

                                viewModel.onSearchQueryChanged(toQuery)

                                if (fromQuery != toQuery) {
                                    Then("uiState is updated with the new query") {
                                        awaitItem().searchState.query shouldBe toQuery
                                    }
                                } else {
                                    Then("no changes occur") {
                                        expectNoEvents()
                                    }
                                }
                            }
                        }
                    }
                }

                When("onDetailSelected is called") {
                    val drink = DrinkSummaryUiModel(id = 1)
                    viewModel.onDetailSelected(drink)

                    Then("navigator is invoked with the correct drink id") {
                        verify { navigator.navigateToDetail(drinkId = drink.id) }
                    }
                }
            }
        },
    )
