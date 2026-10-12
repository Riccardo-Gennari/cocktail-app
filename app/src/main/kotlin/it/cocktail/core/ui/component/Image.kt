package it.cocktail.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import it.cocktail.core.ui.model.ImageResource
import it.cocktail.core.ui.model.ImageUiModel
import it.cocktail.core.ui.model.ImageUri

/**
 * Composable that delegates image rendering to [Image] or [AsyncImage] based on [imageUi] type.
 */
@Composable
fun Image(
    imageUi: ImageUiModel,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
) {
    when (imageUi) {
        is ImageResource -> {
            Image(
                painter = painterResource(imageUi.resId),
                contentDescription = null,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
            )
        }

        is ImageUri -> {
            AsyncImage(
                model = imageUi.uri,
                contentDescription = null,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
            )
        }
    }
}
