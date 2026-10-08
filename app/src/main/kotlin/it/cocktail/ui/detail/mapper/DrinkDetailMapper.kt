package it.cocktail.ui.detail.mapper

import it.cocktail.core.ui.model.ImageUri
import it.cocktail.domain.model.DrinkDetail
import it.cocktail.ui.detail.model.DrinkDetailUiModel

fun DrinkDetail.toUi(): DrinkDetailUiModel =
    DrinkDetailUiModel(
        id = id,
        name = name,
        imageUri = ImageUri(imageUri),
        ingredients = ingredients.map { it.toUi() },
        instructions = instructions,
        tags = listOf(category, alcoholic, glass).filter { it.isNotBlank() },
    )
