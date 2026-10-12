package it.cocktail.data.repository

import it.cocktail.data.datasource.local.PersistentStorage
import it.cocktail.data.datasource.local.mapper.toDomain
import it.cocktail.data.datasource.local.mapper.toLocalDto
import it.cocktail.data.datasource.network.client.CocktailNetworkClient
import it.cocktail.data.datasource.network.mapper.toDomain
import it.cocktail.domain.model.DrinkSummary
import it.cocktail.domain.repository.DrinkSummaryRepository
import javax.inject.Inject

/**
 * Implementation of [DrinkSummaryRepository] using [CocktailNetworkClient] and [PersistentStorage] data sources.
 *
 * @param networkClient the network datasource
 * @param persistentStorage the local datasource
 */
class DefaultDrinkSummaryRepository
    @Inject
    constructor(
        private val networkClient: CocktailNetworkClient,
        private val persistentStorage: PersistentStorage,
    ) : DrinkSummaryRepository {
        override suspend fun getStoredDrinks(): Result<List<DrinkSummary>> =
            persistentStorage.getDrinkSummaries().map { drinks ->
                drinks.map { it.toDomain() }
            }

        override suspend fun storeDrinks(drinks: List<DrinkSummary>): Result<Unit> =
            persistentStorage.storeDrinkSummaries(drinks.map { it.toLocalDto() })

        override suspend fun searchByIngredient(ingredient: String): Result<List<DrinkSummary>> =
            networkClient.searchByIngredient(ingredient).map { it.toDomain() }

        override suspend fun searchByName(name: String): Result<List<DrinkSummary>> =
            networkClient.searchByName(name).map { it.toDomain() }
    }
