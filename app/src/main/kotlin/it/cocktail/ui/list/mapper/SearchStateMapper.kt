package it.cocktail.ui.list.mapper

import it.cocktail.domain.model.DrinkQuery
import it.cocktail.ui.list.model.SearchState
import it.cocktail.ui.list.model.SearchType

fun SearchState.toDrinkQuery(): DrinkQuery =
    when (type) {
        SearchType.Name -> DrinkQuery.ByName(name = query)
        SearchType.Ingredient -> DrinkQuery.ByIngredient(ingredient = query)
    }
