package it.cocktail.ui.detail.model

import androidx.compose.runtime.Immutable
import it.cocktail.core.ui.model.ImageResource
import it.cocktail.core.ui.model.ImageUiModel

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
