package it.cocktail.feature.list.mapper

import it.cocktail.core.ui.model.ImageUri
import it.cocktail.domain.model.DrinkSummary
import it.cocktail.feature.list.model.DrinkSummaryUiModel

fun DrinkSummary.toUi(): DrinkSummaryUiModel =
    DrinkSummaryUiModel(
        id = id,
        name = name,
        imageUri = ImageUri(imageUri),
    )
