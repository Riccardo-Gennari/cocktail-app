package it.cocktail.ui.list.mapper

import it.cocktail.domain.model.DrinkQuery
import it.cocktail.ui.list.model.SearchState
import it.cocktail.ui.list.model.SearchType

fun SearchState.toDrinkQuery(): DrinkQuery =
    when (type) {
        SearchType.NAME -> DrinkQuery.ByName(name = query)
        SearchType.INGREDIENT -> DrinkQuery.ByIngredient(ingredient = query)
    }
