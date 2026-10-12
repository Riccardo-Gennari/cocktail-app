package it.cocktail.domain.repository

import it.cocktail.domain.model.DrinkSummary

/**
 * Single source of truth for [DrinkSummary] data.
 */
interface DrinkSummaryRepository {
    /**
     * Returns the last list of [DrinkSummary] previously stored by [storeDrinks].
     * @return a [Result] containing the list of [DrinkSummary].
     */
    suspend fun getStoredDrinks(): Result<List<DrinkSummary>>

    /**
     * Stores a list of [DrinkSummary] for later retrieval.
     * @return a [Result] indicating the operation outcome.
     */
    suspend fun storeDrinks(drinks: List<DrinkSummary>): Result<Unit>

    /**
     * Fetches a list of all [DrinkSummary] that use [ingredient] in their recipe.
     * @param ingredient the name of the ingredient
     * @return a [Result] containing the list of all matching [DrinkSummary].
     */
    suspend fun searchByIngredient(ingredient: String): Result<List<DrinkSummary>>

    /**
     * Fetches a list of all [DrinkSummary] named [name].
     * @param name the name of the [DrinkSummary]
     * @return a [Result] containing the list of all matching [DrinkSummary].
     */
    suspend fun searchByName(name: String): Result<List<DrinkSummary>>
}
