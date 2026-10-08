package it.cocktail.data.datasource.network.client

import it.cocktail.data.datasource.network.dto.DrinkDetailResponseDto
import it.cocktail.data.datasource.network.dto.DrinksResponseDto

interface CocktailNetworkClient {
    suspend fun getDrinkDetail(id: Int): Result<DrinkDetailResponseDto?>

    suspend fun searchByIngredient(ingredient: String): Result<DrinksResponseDto>

    suspend fun searchByName(name: String): Result<DrinksResponseDto>
}
