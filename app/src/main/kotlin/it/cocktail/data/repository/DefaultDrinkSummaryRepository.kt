package it.cocktail.data.repository

import it.cocktail.data.datasource.network.client.CocktailNetworkClient
import it.cocktail.data.datasource.network.mapper.toDomain
import it.cocktail.domain.model.DrinkSummary
import it.cocktail.domain.repository.DrinkSummaryRepository
import javax.inject.Inject

class DefaultDrinkSummaryRepository
    @Inject
    constructor(
        private val networkClient: CocktailNetworkClient,
    ) : DrinkSummaryRepository {
        override suspend fun searchByIngredient(ingredient: String): Result<List<DrinkSummary>> =
            networkClient.searchByIngredient(ingredient).map { it.toDomain() }

        override suspend fun searchByName(query: String): Result<List<DrinkSummary>> =
            networkClient.searchByName(query).map { it.toDomain() }
    }
