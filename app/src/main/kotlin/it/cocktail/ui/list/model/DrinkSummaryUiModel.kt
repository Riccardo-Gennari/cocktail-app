package it.cocktail.ui.list.model

import androidx.compose.runtime.Immutable
import it.cocktail.core.ui.model.ImageResource
import it.cocktail.core.ui.model.ImageUiModel

@Immutable
data class DrinkSummaryUiModel(
    val id: Int = 0,
    val name: String = "",
    val imageUri: ImageUiModel? = null,
) {
    companion object {
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
