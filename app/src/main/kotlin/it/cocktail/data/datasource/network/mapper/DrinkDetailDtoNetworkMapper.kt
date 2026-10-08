package it.cocktail.data.datasource.network.mapper

import it.cocktail.data.datasource.network.client.CocktailEndpoint
import it.cocktail.data.datasource.network.dto.DrinkDetailNetworkDto
import it.cocktail.domain.model.DrinkDetail
import it.cocktail.domain.model.Ingredient

fun DrinkDetailNetworkDto.toDomain(): DrinkDetail? {
    val id = id ?: return null
    val name = name?.takeIf { it.isNotBlank() } ?: return null

    return DrinkDetail(
        id = id,
        name = name,
        category = category.orEmpty(),
        alcoholic = alcoholic.orEmpty(),
        glass = glass.orEmpty(),
        instructions = instructions.orEmpty(),
        imageUri = imageUri.orEmpty(),
        ingredients = toIngredients(),
    )
}

private fun DrinkDetailNetworkDto.toIngredients(): List<Ingredient> =
    listOf(
        ingredient1 to measure1,
        ingredient2 to measure2,
        ingredient3 to measure3,
        ingredient4 to measure4,
        ingredient5 to measure5,
        ingredient6 to measure6,
        ingredient7 to measure7,
        ingredient8 to measure8,
        ingredient9 to measure9,
        ingredient10 to measure10,
        ingredient11 to measure11,
        ingredient12 to measure12,
        ingredient13 to measure13,
        ingredient14 to measure14,
        ingredient15 to measure15,
    ).mapNotNull { (ingredient, measure) ->
        // trim is needed because api often returns trailing spaces
        val name = ingredient?.trim()?.takeIf { it.isNotEmpty() } ?: return@mapNotNull null
        Ingredient(
            name = name,
            measure = measure?.trim()?.takeIf { it.isNotEmpty() }, // measure is allowed to be null
            imageUri = CocktailEndpoint.ingredientThumbnailPath(ingredientName = name),
        )
    }
