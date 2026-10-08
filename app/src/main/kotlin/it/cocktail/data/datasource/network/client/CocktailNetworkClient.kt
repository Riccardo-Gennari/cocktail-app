package it.cocktail.data.datasource.network.client

import it.cocktail.data.datasource.network.dto.DrinkDetailResponseNetworkDto
import it.cocktail.data.datasource.network.dto.DrinksResponseNetworkDto

interface CocktailNetworkClient {
    suspend fun getDrinkDetail(id: Int): Result<DrinkDetailResponseNetworkDto?>

    suspend fun searchByIngredient(ingredient: String): Result<DrinksResponseNetworkDto>

    suspend fun searchByName(name: String): Result<DrinksResponseNetworkDto>
}
