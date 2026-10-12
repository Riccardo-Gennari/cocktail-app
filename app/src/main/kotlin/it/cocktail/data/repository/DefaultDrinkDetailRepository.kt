package it.cocktail.data.repository

import it.cocktail.data.datasource.local.PersistentStorage
import it.cocktail.data.datasource.local.mapper.toDomain
import it.cocktail.data.datasource.local.mapper.toLocalDto
import it.cocktail.data.datasource.network.client.CocktailNetworkClient
import it.cocktail.data.datasource.network.mapper.toDomain
import it.cocktail.domain.model.DrinkDetail
import it.cocktail.domain.repository.DrinkDetailRepository
import javax.inject.Inject

/**
 * Implementation of [DrinkDetailRepository] using [CocktailNetworkClient] and [PersistentStorage] data sources.
 *
 * @param networkClient the network datasource
 * @param persistentStorage the local datasource
 */
class DefaultDrinkDetailRepository
    @Inject
    constructor(
        private val networkClient: CocktailNetworkClient,
        private val persistentStorage: PersistentStorage,
    ) : DrinkDetailRepository {
        override suspend fun getDetail(
            id: Int,
            forceRefresh: Boolean,
        ): Result<DrinkDetail?> =
            if (forceRefresh) {
                fetchAndCacheNetworkDetail(id) // ignore cached data
            } else {
                persistentStorage.getDrinkDetail(id).fold(
                    onSuccess = { storedDto ->
                        if (storedDto != null) {
                            Result.success(storedDto.toDomain())
                        } else {
                            fetchAndCacheNetworkDetail(id)
                        }
                    },
                    onFailure = { fetchAndCacheNetworkDetail(id) },
                )
            }

        private suspend fun fetchAndCacheNetworkDetail(id: Int): Result<DrinkDetail?> =
            networkClient
                .getDrinkDetail(id)
                .map { response -> response?.drinks?.firstOrNull()?.toDomain() }
                .onSuccess { domainDrink ->
                    if (domainDrink != null) {
                        // cache the result, enabling faster offline retrieval for later queries
                        persistentStorage.storeDrinkDetail(domainDrink.toLocalDto())
                    }
                }
    }
