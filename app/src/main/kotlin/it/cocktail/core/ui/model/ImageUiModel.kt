package it.cocktail.core.ui.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import coil3.Uri
import coil3.toUri
import it.cocktail.R

/**
 * Represents different ways to render an image in ui.
 */
@Stable
sealed interface ImageUiModel

/**
 * implementation of [ImageUiModel] that carries an Android [DrawableRes] for rendering of local images.
 * @property resId the image's [DrawableRes]
 */
@JvmInline
@Stable
value class ImageResource(
    @DrawableRes val resId: Int,
) : ImageUiModel {
    companion object {
        val drinkSample = ImageResource(R.drawable.drink_sample)
    }
}

/**
 * implementation of [ImageUiModel] that carries a [Uri] for rendering of remote images.
 * @property uri the image's [Uri]
 */
@JvmInline
@Stable
value class ImageUri(
    val uri: Uri,
) : ImageUiModel {
    constructor(uri: String) : this(uri = uri.toUri())
}
