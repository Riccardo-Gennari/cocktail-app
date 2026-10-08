package it.cocktail.data.datasource.network.mapper

import it.cocktail.data.datasource.network.client.CocktailEndpoint
import it.cocktail.data.datasource.network.dto.DrinkSummaryDto
import it.cocktail.domain.model.DrinkSummary

fun DrinkSummaryDto.toDomain(): DrinkSummary? {
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
