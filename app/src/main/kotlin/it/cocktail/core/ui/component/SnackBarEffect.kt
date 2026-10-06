package it.cocktail.core.ui.component

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun SnackBarEffect(
    snackBarState: SnackbarHostState,
    message: String?,
    onMessageShown: () -> Unit = {},
) {
    val currentOnMessageShown by rememberUpdatedState(onMessageShown)

    LaunchedEffect(message) {
        if (!message.isNullOrBlank()) {
            snackBarState.showSnackbar(message)
            currentOnMessageShown()
        }
    }
}
