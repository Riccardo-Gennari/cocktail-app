package it.cocktail.core.ui.model

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

typealias UiMessageResult = SnackbarResult
typealias UiMessageDuration = SnackbarDuration

@Immutable
data class UiMessage(
    val message: UiText,
    val actionLabel: UiText? = null,
    val withDismissAction: Boolean = false,
    val duration: UiMessageDuration = if (actionLabel == null) Short else Indefinite,
) {
    @Composable
    fun asSnackBarVisuals() =
        object : SnackbarVisuals {
            override val message: String = this@UiMessage.message.asString()
            override val actionLabel: String? = this@UiMessage.actionLabel?.asString()
            override val withDismissAction: Boolean = this@UiMessage.withDismissAction
            override val duration: SnackbarDuration = this@UiMessage.duration
        }
}
