package it.cocktail.data.repository

import it.cocktail.data.datasource.network.client.CocktailNetworkClient
import it.cocktail.data.datasource.network.mapper.toDomain
import it.cocktail.domain.model.DrinkDetail
import it.cocktail.domain.repository.DrinkDetailRepository
import javax.inject.Inject

class DefaultDrinkDetailRepository
    @Inject
    constructor(
        private val networkClient: CocktailNetworkClient,
    ) : DrinkDetailRepository {
        override suspend fun getDetail(id: Int): Result<DrinkDetail?> =
            networkClient.getDrinkDetail(id).map { it?.drinks?.firstOrNull()?.toDomain() }
    }
