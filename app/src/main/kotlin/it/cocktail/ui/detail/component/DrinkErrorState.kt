package it.cocktail.ui.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.cocktail.R
import it.cocktail.core.ui.model.UiError

/**
 * Shows an error message with retry button.
 * Message depends on [error] type.
 */
@Composable
fun DrinkErrorState(
    error: UiError,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val messageRes = remember(error) {
        when (error) {
            UiError.NoConnection -> R.string.no_internet_connection
            UiError.Generic -> R.string.something_went_wrong
        }
    }

    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(64.dp)
                .background(color = colors.primaryContainer, shape = CircleShape),
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_error_24),
                contentDescription = null,
                tint = colors.onPrimaryContainer,
                modifier = Modifier.size(32.dp),
            )
        }

        Text(
            text = stringResource(messageRes),
            style = typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = colors.onSurface,
            textAlign = TextAlign.Center,
        )

        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DrinkErrorStatePreview() {
    DrinkErrorState(error = UiError.Generic)
}

@Preview(showBackground = true)
@Composable
private fun DrinkNoConnectionErrorStatePreview() {
    DrinkErrorState(error = UiError.NoConnection)
}
