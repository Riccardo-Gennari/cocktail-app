package it.cocktail.domain.repository

import it.cocktail.domain.model.DrinkSummary

interface DrinkSummaryRepository {
    suspend fun getStoredDrinks(): Result<List<DrinkSummary>>

    suspend fun storeDrinks(drinks: List<DrinkSummary>): Result<Unit>

    suspend fun searchByIngredient(ingredient: String): Result<List<DrinkSummary>>

    suspend fun searchByName(query: String): Result<List<DrinkSummary>>
}
