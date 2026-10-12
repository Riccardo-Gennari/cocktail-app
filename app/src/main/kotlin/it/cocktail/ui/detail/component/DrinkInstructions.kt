package it.cocktail.ui.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.cocktail.R
import it.cocktail.core.ui.component.StyleDefaults
import it.cocktail.ui.detail.model.DrinkDetailUiModel

/**
 * Shows the [instructions] wrapped in a [ElevatedCard] and preceded by a fixed section title.
 */
@Composable
fun DrinkInstructions(
    instructions: String,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.instructions),
            style = typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = colors.onSurface,
        )

        ElevatedCard(
            shape = StyleDefaults.cardShape,
            colors = CardDefaults.elevatedCardColors(containerColor = colors.surfaceContainerLow),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = instructions,
                style = typography.bodyMedium,
                color = colors.onSurface,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Preview
@Composable
private fun DrinkInstructionsPreview() {
    DrinkInstructions(instructions = DrinkDetailUiModel.sample().instructions)
}
