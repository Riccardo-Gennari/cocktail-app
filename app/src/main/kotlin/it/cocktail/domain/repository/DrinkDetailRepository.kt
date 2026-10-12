package it.cocktail.domain.repository

import it.cocktail.domain.model.DrinkDetail

/**
 * Single source of truth for [DrinkDetail] data.
 */
interface DrinkDetailRepository {
    /**
     * Fetches a [DrinkDetail] based on [id].
     * @param id the ID of the requested drink
     * @param forceRefresh if true, requests fresh data from source of truth, ignoring any local cache.
     * @return a [Result] containing the [DrinkDetail] matching [id], or null if none was found.
     */
    suspend fun getDetail(
        id: Int,
        forceRefresh: Boolean = false,
    ): Result<DrinkDetail?>
}
