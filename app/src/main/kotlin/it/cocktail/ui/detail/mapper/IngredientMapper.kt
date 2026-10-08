package it.cocktail.ui.detail.mapper

import it.cocktail.core.ui.model.ImageUri
import it.cocktail.domain.model.Ingredient
import it.cocktail.ui.detail.model.IngredientUiModel

fun Ingredient.toUi() =
    IngredientUiModel(
        name = name,
        measure = measure,
        image = ImageUri(imageUri),
    )
