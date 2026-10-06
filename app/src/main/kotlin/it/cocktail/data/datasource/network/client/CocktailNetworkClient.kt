package it.cocktail.data.datasource.network.client

import it.cocktail.data.datasource.network.dto.DrinksResponseDto

interface CocktailNetworkClient {
    suspend fun searchByName(name: String): Result<DrinksResponseDto>

    suspend fun searchByIngredient(ingredient: String): Result<DrinksResponseDto>
}
