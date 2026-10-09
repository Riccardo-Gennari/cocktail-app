package it.cocktail.ui.list

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.test
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import it.cocktail.core.common.log.Logger
import it.cocktail.domain.model.DrinkQuery
import it.cocktail.domain.model.DrinkSummary
import it.cocktail.domain.usecase.GetLastDrinksUseCase
import it.cocktail.domain.usecase.SearchDrinksUseCase
import it.cocktail.ui.list.mapper.toDrinkQuery
import it.cocktail.ui.list.mapper.toUi
import it.cocktail.ui.list.model.DrinkListUiState
import it.cocktail.ui.list.model.DrinkSummaryUiModel
import it.cocktail.ui.list.model.SearchState
import it.cocktail.ui.list.model.SearchType
import it.cocktail.ui.list.navigation.DrinkListNavigator
import it.cocktail.util.awaitUntil
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class DrinkListViewModelTest :
    BehaviorSpec({
        Given("an instance") {
            val searchDrinks = mockk<SearchDrinksUseCase>()
            val getLastDrinks = mockk<GetLastDrinksUseCase> {
                coEvery { this@mockk.invoke() } returns Result.success(emptyList())
            }
            val navigator = mockk<DrinkListNavigator>(relaxed = true)
            val log = mockk<Logger>(relaxed = true)
            val viewModel = DrinkListViewModel(getLastDrinks, log, navigator, searchDrinks)

            val drinks = List(3) { DrinkSummary(id = it, name = "name $it", imageUri = "image $it") }

            fun mockSearchDrinks(
                query: DrinkQuery,
                result: Result<List<DrinkSummary>>,
            ) {
                coEvery { searchDrinks(query) } coAnswers {
                    delay(1.seconds)
                    result
                }
            }

            When("it is initialized") {
                Then("it exposes a uiState") {
                    viewModel.uiState.test { awaitItem() }
                }

                Then("it fetches last drinks and populates drinkList") {
                    val storedDrinks = List(2) { DrinkSummary(id = it, name = "stored $it", imageUri = "image $it") }
                    val getLastDrinksMock = mockk<GetLastDrinksUseCase> {
                        coEvery { this@mockk.invoke() } returns Result.success(storedDrinks)
                    }
                    val viewModelWithStored = DrinkListViewModel(getLastDrinksMock, log, navigator, searchDrinks)
                    viewModelWithStored.uiState.test {
                        val state = awaitUntil { it.drinkList.isNotEmpty() }
                        state.drinkList shouldBe storedDrinks.map { it.toUi() }
                    }
                }
            }

            When("a valid search query is set") {
                withData(
                    SearchState(query = "name", type = SearchType.NAME),
                    SearchState(query = "ingredient", type = SearchType.INGREDIENT),
                ) { searchState ->
                    val query = searchState.toDrinkQuery()
                    viewModel.onSearchTypeChanged(searchState.type)
                    viewModel.onSearchQueryChanged(searchState.query)

                    // wait until searchState has the query before triggering search
                    suspend fun ReceiveTurbine<DrinkListUiState>.search() {
                        awaitUntil { it.searchState == searchState }
                        viewModel.onSearch()
                    }

                    Then("uiState is updated with the new query") {
                        viewModel.uiState.test { awaitUntil { it.searchState == searchState } }
                    }

                    And("the search succeeds") {
                        mockSearchDrinks(query, Result.success(drinks))

                        Then("uiState is updated with the new data") {
                            viewModel.uiState.test {
                                search()
                                awaitUntil { it.isLoading }
                                awaitUntil { !it.isLoading && it.drinkList.isNotEmpty() }
                                    .drinkList shouldBe drinks.map { it.toUi() }
                            }
                        }
                    }

                    And("the search fails") {
                        mockSearchDrinks(query, Result.failure(IllegalStateException("exception msg")))

                        Then("uiState has empty data and an error state") {
                            viewModel.uiState.test {
                                search()
                                awaitUntil { it.isLoading }
                                val state = awaitUntil { !it.isLoading && it.isError }
                                state.drinkList.shouldBeEmpty()
                                state.isError shouldBe true
                            }
                        }

                        Then("on refresh, errorState is cleared and drinks fetched") {
                            viewModel.uiState.test {
                                search()
                                awaitUntil { it.isError }

                                mockSearchDrinks(query, Result.success(drinks))
                                viewModel.onRefresh()

                                val state = awaitUntil { !it.isLoading && it.drinkList.isNotEmpty() }
                                state.isError shouldBe false
                                state.drinkList shouldBe drinks.map { it.toUi() }
                            }
                        }
                    }
                }
            }

            When("the search type changes") {
                withData(
                    nameFn = { (from, to) -> "from $from to $to" },
                    SearchType.entries.flatMap { from -> SearchType.entries.map { to -> from to to } },
                ) { (from, to) ->
                    viewModel.onSearchTypeChanged(from)
                    viewModel.onSearchQueryChanged("search text")

                    viewModel.uiState.test {
                        awaitUntil { it.searchState == SearchState(query = "search text", type = from) }

                        viewModel.onSearchTypeChanged(to)

                        if (from == to) {
                            expectNoEvents()
                        } else {
                            awaitUntil { it.searchState.type == to }.searchState.query.shouldBeEmpty()
                        }
                    }
                }
            }

            When("the search query changes") {
                withData(
                    nameFn = { (from, to) -> "from '$from' to '$to'" },
                    listOf("" to "new query", "old query" to "new query", "same query" to "same query", "" to ""),
                ) { (from, to) ->
                    viewModel.onSearchQueryChanged(from)

                    viewModel.uiState.test {
                        awaitUntil { it.searchState.query == from }

                        viewModel.onSearchQueryChanged(to)

                        if (from == to) expectNoEvents() else awaitUntil { it.searchState.query == to }
                    }
                }
            }

            When("onDetailSelected is called") {
                Then("navigator is invoked with the correct drink id") {
                    val drink = DrinkSummaryUiModel(id = 1)
                    viewModel.onDetailSelected(drink)
                    verify(exactly = 1) { navigator.navigateToDetail(drinkId = drink.id) }
                }
            }
        }
    })
