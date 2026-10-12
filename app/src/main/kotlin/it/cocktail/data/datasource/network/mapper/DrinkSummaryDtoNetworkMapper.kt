package it.cocktail.data.datasource.network.mapper

import it.cocktail.data.datasource.network.client.CocktailEndpoint
import it.cocktail.data.datasource.network.dto.DrinkSummaryNetworkDto
import it.cocktail.data.datasource.network.dto.DrinksResponseNetworkDto
import it.cocktail.domain.model.DrinkSummary

/**
 * Maps [DrinkSummaryNetworkDto] to a domain [DrinkSummary].
 */
fun DrinkSummaryNetworkDto.toDomain(): DrinkSummary? {
    val id = id ?: return null
    val name = name?.takeIf { it.isNotBlank() } ?: return null
    return DrinkSummary(
        id = id,
        name = name,
        imageUri = imageUri
            ?.takeIf { it.isNotBlank() }
            ?.let { CocktailEndpoint.drinkSummaryThumbnailPath(it) }
            .orEmpty(),
    )
}
