package it.cocktail.domain.model

sealed interface DrinkQuery {
    data class ByName(
        val name: String,
    ) : DrinkQuery

    data class ByIngredient(
        val ingredient: String,
    ) : DrinkQuery
}
