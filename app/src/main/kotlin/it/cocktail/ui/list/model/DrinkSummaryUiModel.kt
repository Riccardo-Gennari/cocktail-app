package it.cocktail.ui.list.model

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Immutable
import it.cocktail.core.ui.model.ImageResource
import it.cocktail.core.ui.model.ImageUiModel

/**
 * Ui model representing the summary of a cocktail recipe.
 */
@Immutable
data class DrinkSummaryUiModel(
    val id: Int = 0,
    val name: String = "",
    val imageUri: ImageUiModel? = null,
) {
    companion object {
        /** @return a [DrinkSummaryUiModel] instance filled with example data. Meant for tests and ui previews. */
        @VisibleForTesting
        fun sample(count: Int) =
            List(count) { index ->
                DrinkSummaryUiModel(
                    id = index,
                    name = "Drink $index",
                    imageUri = ImageResource.drinkSample,
                )
            }
    }
}
