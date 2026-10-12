package it.cocktail.ui.detail.model

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Immutable
import it.cocktail.core.ui.model.ImageResource
import it.cocktail.core.ui.model.ImageUiModel
import org.jetbrains.annotations.TestOnly

/**
 * Ui model representing the details of a cocktail recipe.
 */
@Immutable
data class DrinkDetailUiModel(
    val id: Int = 0,
    val name: String = "",
    val instructions: String = "",
    val imageUri: ImageUiModel? = null,
    val ingredients: List<IngredientUiModel> = emptyList(),
    val tags: List<String> = emptyList(),
) {
    companion object {
        /** @return a [DrinkDetailUiModel] instance filled with example data. Meant for tests and ui previews. */
        @VisibleForTesting
        fun sample() =
            DrinkDetailUiModel(
                id = 11007,
                name = "Margarita",
                instructions = "Rub the rim of the glass with the lime slice to make the salt stick to it. " +
                    "Shake the other ingredients with ice, then pour into the glass.",
                imageUri = ImageResource.drinkSample,
                ingredients = IngredientUiModel.sample(4),
                tags = listOf("Ordinary Drink", "Alcoholic", "Cocktail glass"),
            )
    }
}
