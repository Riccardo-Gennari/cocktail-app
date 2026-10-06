package it.cocktail.data.repository

import it.cocktail.data.datasource.network.client.CocktailNetworkClient
import it.cocktail.domain.mapper.toDomain
import it.cocktail.domain.model.DrinkSummary
import it.cocktail.domain.repository.DrinkSummaryRepository

class DefaultDrinkSummaryRepository(
    private val networkClient: CocktailNetworkClient,
) : DrinkSummaryRepository {
    override suspend fun searchByName(query: String): Result<List<DrinkSummary>> =
        networkClient.searchByName(query).map { it.toDomain() }

    override suspend fun searchByIngredient(ingredient: String): Result<List<DrinkSummary>> =
        networkClient.searchByIngredient(ingredient).map { it.toDomain() }
}
