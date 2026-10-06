package it.cocktail.feature.list.mapper

import it.cocktail.domain.model.DrinkQuery
import it.cocktail.feature.list.model.SearchState
import it.cocktail.feature.list.model.SearchType

fun SearchState.toDrinkQuery(): DrinkQuery =
    when (type) {
        SearchType.NAME -> DrinkQuery.ByName(name = query)
        SearchType.INGREDIENT -> DrinkQuery.ByIngredient(ingredient = query)
    }
