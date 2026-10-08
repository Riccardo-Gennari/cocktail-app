package it.cocktail.data.datasource.local

import it.cocktail.data.datasource.local.dto.DrinkDetailLocalDto
import it.cocktail.data.datasource.local.dto.DrinkSummaryLocalDto

interface PersistentStorage {
    suspend fun storeDrinkSummaries(drinkSummaries: List<DrinkSummaryLocalDto>): Result<Unit>

    suspend fun getDrinkSummaries(): Result<List<DrinkSummaryLocalDto>>

    suspend fun storeDrinkDetail(drinkDetail: DrinkDetailLocalDto): Result<Unit>

    suspend fun getDrinkDetail(id: Int): Result<DrinkDetailLocalDto?>
}
