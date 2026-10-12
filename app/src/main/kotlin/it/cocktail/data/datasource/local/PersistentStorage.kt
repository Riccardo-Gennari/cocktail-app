package it.cocktail.data.datasource.local

import it.cocktail.data.datasource.local.dto.DrinkDetailLocalDto
import it.cocktail.data.datasource.local.dto.DrinkSummaryLocalDto

/**
 * Provides local, persistent storage to the app.
 */
interface PersistentStorage {
    /**
     * Persists a list of [DrinkSummaryLocalDto] into local persistent storage and returns a [Result] indicating operation outcome.
     *
     * @param drinkSummaries drinks to be persisted
     * @return a [Result] indicating operation outcome.
     */
    suspend fun storeDrinkSummaries(drinkSummaries: List<DrinkSummaryLocalDto>): Result<Unit>

    /**
     * Fetches all [DrinkSummaryLocalDto] present in the local storage.
     *
     * @return a [Result] containing a list of [DrinkSummaryLocalDto] or a runtime error.
     */
    suspend fun getDrinkSummaries(): Result<List<DrinkSummaryLocalDto>>

    /**
     * Persists a [DrinkDetailLocalDto] into local persistent storage and returns a [Result] indicating operation outcome.
     *
     * @param drinkDetail drink to be persisted
     * @return a [Result] indicating operation outcome.
     */
    suspend fun storeDrinkDetail(drinkDetail: DrinkDetailLocalDto): Result<Unit>

    /**
     * Fetches the [DrinkDetailLocalDto] matching [id], or null if not present.
     *
     * @return a [Result] containing either the matching [DrinkDetailLocalDto], or null.
     */
    suspend fun getDrinkDetail(id: Int): Result<DrinkDetailLocalDto?>
}
