package it.cocktail.data.datasource.network.client

import it.cocktail.data.datasource.network.dto.DrinkDetailResponseNetworkDto
import it.cocktail.data.datasource.network.dto.DrinksResponseNetworkDto

/**
 * Provides network access to the CocktailDB API, in order to query data about cocktails.
 */
interface CocktailNetworkClient {
    /**
     *  Fetches the full info about a drink, based on ID.
     *
     *  @param id the ID of the requested drink.
     *  @return a [Result] containing the requested [DrinkDetailResponseNetworkDto], or null if no matching drinks were found.
     */
    suspend fun getDrinkDetail(id: Int): Result<DrinkDetailResponseNetworkDto?>

    /**
     *  Fetches a list of drinks, that use [ingredient] in their recipe.
     *
     *  @param ingredient the name of the ingredient to query.
     *  @return a [Result] containing the requested [DrinksResponseNetworkDto].
     */
    suspend fun searchByIngredient(ingredient: String): Result<DrinksResponseNetworkDto>

    /**
     *  Fetches a list of drinks, whose names contain [name].
     *
     *  @param name the name of the drink to query.
     *  @return a [Result] containing the requested [DrinksResponseNetworkDto].
     */
    suspend fun searchByName(name: String): Result<DrinksResponseNetworkDto>
}
