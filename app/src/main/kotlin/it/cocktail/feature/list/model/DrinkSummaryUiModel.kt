package it.cocktail.feature.list.model

import androidx.compose.runtime.Stable
import it.cocktail.R
import it.cocktail.core.ui.model.ImageResource
import it.cocktail.core.ui.model.ImageUiModel

@Stable
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
                    imageUri = ImageResource(R.drawable.default_img),
                )
            }
    }
}
