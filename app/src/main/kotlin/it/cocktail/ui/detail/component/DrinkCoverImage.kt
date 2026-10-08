package it.cocktail.ui.detail.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.cocktail.core.ui.component.Image
import it.cocktail.core.ui.component.StyleDefaults
import it.cocktail.core.ui.model.ImageResource
import it.cocktail.core.ui.model.ImageUiModel

@Composable
fun DrinkCoverImage(
    image: ImageUiModel,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        shape = StyleDefaults.cardShape,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        modifier = modifier.size(200.dp),
    ) {
        Image(
            imageUi = image,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview
@Composable
private fun DrinkCoverImagePreview() {
    DrinkCoverImage(ImageResource.drinkSample)
}
