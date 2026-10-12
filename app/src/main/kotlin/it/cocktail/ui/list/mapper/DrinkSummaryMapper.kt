package it.cocktail.ui.list.mapper

import it.cocktail.core.ui.model.ImageUri
import it.cocktail.domain.model.DrinkSummary
import it.cocktail.ui.list.model.DrinkSummaryUiModel

/**
 * Maps [DrinkSummary] to its UI equivalent [DrinkSummaryUiModel].
 */
fun DrinkSummary.toUi(): DrinkSummaryUiModel =
    DrinkSummaryUiModel(
        id = id,
        name = name,
        imageUri = ImageUri(imageUri),
    )
