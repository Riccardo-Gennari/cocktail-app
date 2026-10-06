package it.cocktail.core.ui.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import coil3.Uri
import coil3.toUri

@Stable
sealed interface ImageUiModel

@JvmInline
@Stable
value class ImageResource(
    @DrawableRes val resId: Int,
) : ImageUiModel

@JvmInline
@Stable
value class ImageUri(
    val uri: Uri,
) : ImageUiModel {
    constructor(uri: String) : this(uri = uri.toUri())
}
