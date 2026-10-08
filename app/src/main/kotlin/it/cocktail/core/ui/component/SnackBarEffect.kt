package it.cocktail.core.ui.component

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun SnackBarEffect(
    snackBarState: SnackbarHostState,
    snackBarVisuals: SnackbarVisuals?,
    onSnackBarResult: (SnackbarResult) -> Unit = {},
) {
    val currentOnSnackBarResult by rememberUpdatedState(onSnackBarResult)

    LaunchedEffect(snackBarVisuals) {
        if (!snackBarVisuals?.message.isNullOrBlank()) {
            val result = snackBarState.showSnackbar(snackBarVisuals)
            currentOnSnackBarResult(result)
        }
    }
}
