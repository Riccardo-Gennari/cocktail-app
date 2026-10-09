package it.cocktail.ui.list.component

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.res.stringResource
import it.cocktail.R

@Composable
fun ErrorSnackBarEffect(
    snackBarState: SnackbarHostState,
    isError: Boolean,
    onRetry: () -> Unit = {},
) {
    val currentOnRetry by rememberUpdatedState(onRetry)
    val message = stringResource(R.string.something_went_wrong)
    val actionLabel = stringResource(R.string.retry)

    LaunchedEffect(isError) {
        if (isError) {
            val result = snackBarState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                withDismissAction = true,
            )
            if (result == SnackbarResult.ActionPerformed) {
                currentOnRetry()
            }
        }
    }
}
