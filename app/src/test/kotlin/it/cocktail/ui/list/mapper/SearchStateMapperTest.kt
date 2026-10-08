package it.cocktail.ui.list.mapper

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import it.cocktail.domain.model.DrinkQuery
import it.cocktail.ui.list.model.SearchState
import it.cocktail.ui.list.model.SearchType

class SearchStateMapperTest :
    BehaviorSpec({
        Given("an instance") {
            When("SearchType is ${SearchType.NAME}") {
                val query = "name"
                val state = SearchState(query = query, type = SearchType.NAME)

                Then("toDrinkQuery should return DrinkQuery.ByName") {
                    state.toDrinkQuery() shouldBe DrinkQuery.ByName(name = query)
                }
            }

            When("SearchType is ${SearchType.INGREDIENT}") {
                val query = "ingredient"
                val state = SearchState(query = query, type = SearchType.INGREDIENT)

                Then("toDrinkQuery should return DrinkQuery.ByIngredient") {
                    state.toDrinkQuery() shouldBe DrinkQuery.ByIngredient(ingredient = query)
                }
            }
        }
    })
