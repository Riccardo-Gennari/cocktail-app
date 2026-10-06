package it.cocktail.domain.repository

import it.cocktail.domain.model.DrinkSummary

interface DrinkSummaryRepository {
    suspend fun searchByName(query: String): Result<List<DrinkSummary>>

    suspend fun searchByIngredient(ingredient: String): Result<List<DrinkSummary>>
}
