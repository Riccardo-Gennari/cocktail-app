package it.cocktail.ui.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Shows each tag in [tags] as a [SuggestionChip] inside a [Column].
 *
 * If [tags] could contain many elements, passing verticalScroll into the [modifier] chain is advisable.
 */
@Composable
fun DrinkDetailInfoChips(
    tags: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        tags.forEach { tag ->
            SuggestionChip(
                onClick = {},
                label = {
                    Text(
                        text = tag,
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
            )
        }
    }
}

@Preview
@Composable
private fun DrinkDetailInfoChipsPreview() {
    DrinkDetailInfoChips(
        tags = listOf("Cocktail", "Alcoholic", "Collins glass"),
    )
}
