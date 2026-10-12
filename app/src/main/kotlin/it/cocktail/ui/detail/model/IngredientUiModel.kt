package it.cocktail.ui.detail.model

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Immutable
import it.cocktail.core.ui.model.ImageResource
import it.cocktail.core.ui.model.ImageUiModel

/**
 * Ui model representing an ingredient used to prepare cocktails.
 */
@Immutable
data class IngredientUiModel(
    val name: String = "",
    val measure: String? = null,
    val image: ImageUiModel? = null,
) {
    companion object {
        /** @return a [IngredientUiModel] instance filled with example data. Meant for tests and ui previews. */
        @VisibleForTesting
        fun sample(count: Int) =
            List(count) { index ->
                when (index) {
                    0 -> IngredientUiModel(name = "Tequila", measure = "1 1/2 oz", image = ImageResource.drinkSample)

                    1 -> IngredientUiModel(name = "Triple sec", measure = "1/2 oz", image = ImageResource.drinkSample)

                    2 -> IngredientUiModel(name = "Lime juice", measure = "1 oz", image = null)

                    3 -> IngredientUiModel(name = "Salt", measure = null, image = ImageResource.drinkSample)

                    else -> IngredientUiModel(
                        name = "ingredient-$index",
                        measure = "measure-$index",
                        image = ImageResource.drinkSample,
                    )
                }
            }
    }
}
