package it.cocktail.data.datasource.local.room

import it.cocktail.core.common.log.Logger
import it.cocktail.core.common.log.logTag
import it.cocktail.core.common.util.runCatchingCancelable
import it.cocktail.data.datasource.local.PersistentStorage
import it.cocktail.data.datasource.local.dto.DrinkDetailLocalDto
import it.cocktail.data.datasource.local.dto.DrinkSummaryLocalDto
import javax.inject.Inject

class RoomPersistentStorage
    @Inject
    constructor(
        private val drinksDao: DrinksDao,
        private val log: Logger,
    ) : PersistentStorage {
        override suspend fun storeDrinkSummaries(drinkSummaries: List<DrinkSummaryLocalDto>): Result<Unit> =
            runCatchingCancelable {
                log.debug(TAG, "Storing drink summaries: $drinkSummaries")
                drinksDao.replaceDrinkSummaries(drinkSummaries)
            }

        override suspend fun getDrinkSummaries(): Result<List<DrinkSummaryLocalDto>> =
            runCatchingCancelable {
                log.debug(TAG, "Fetching drink summaries")
                drinksDao.getDrinkSummaries()
            }

        override suspend fun storeDrinkDetail(drinkDetail: DrinkDetailLocalDto): Result<Unit> =
            runCatchingCancelable {
                log.debug(TAG, "Storing drink detail for id: ${drinkDetail.id}")
                drinksDao.insertDrinkDetail(drinkDetail)
            }

        override suspend fun getDrinkDetail(id: Int): Result<DrinkDetailLocalDto?> =
            runCatchingCancelable {
                log.debug(TAG, "Fetching drink detail for id: $id")
                drinksDao.getDrinkDetail(id)
            }

        companion object {
            val TAG = RoomPersistentStorage::class.logTag()
        }
    }
