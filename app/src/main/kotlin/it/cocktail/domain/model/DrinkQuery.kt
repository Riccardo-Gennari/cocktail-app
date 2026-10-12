package it.cocktail.domain.model

/**
 * Represents the possible ways to query cocktails.
 */
sealed interface DrinkQuery {
    /**
     * Query for searching cocktails by name.
     *
     * @property name The name to query for.
     */
    data class ByName(
        val name: String,
    ) : DrinkQuery

    /**
     * Query for searching cocktails containing a specific ingredient.
     *
     * @property ingredient The name of the ingredient to query.
     */
    data class ByIngredient(
        val ingredient: String,
    ) : DrinkQuery
}
