package it.cocktail.ui.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

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
