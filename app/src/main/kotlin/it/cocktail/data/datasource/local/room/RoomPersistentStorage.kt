package it.cocktail.data.datasource.local.room

import it.cocktail.core.common.util.runCatchingCancelable
import it.cocktail.data.datasource.local.PersistentStorage
import it.cocktail.data.datasource.local.dto.DrinkDetailLocalDto
import it.cocktail.data.datasource.local.dto.DrinkSummaryLocalDto
import javax.inject.Inject

class RoomPersistentStorage
    @Inject
    constructor(
        private val drinksDao: DrinksDao,
    ) : PersistentStorage {
        override suspend fun storeDrinkSummaries(drinkSummaries: List<DrinkSummaryLocalDto>): Result<Unit> =
            runCatchingCancelable {
                drinksDao.replaceDrinkSummaries(drinkSummaries)
            }

        override suspend fun getDrinkSummaries(): Result<List<DrinkSummaryLocalDto>> =
            runCatchingCancelable {
                drinksDao.getDrinkSummaries()
            }

        override suspend fun storeDrinkDetail(drinkDetail: DrinkDetailLocalDto): Result<Unit> =
            runCatchingCancelable {
                drinksDao.insertDrinkDetail(drinkDetail)
            }

        override suspend fun getDrinkDetail(id: Int): Result<DrinkDetailLocalDto?> =
            runCatchingCancelable {
                drinksDao.getDrinkDetail(id)
            }
    }
