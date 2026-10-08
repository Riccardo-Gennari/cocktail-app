package it.cocktail.ui.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.cocktail.R
import it.cocktail.core.ui.component.Image
import it.cocktail.core.ui.component.StyleDefaults
import it.cocktail.ui.detail.model.IngredientUiModel

@Composable
fun DrinkIngredients(
    ingredients: List<IngredientUiModel>,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.ingredients),
            style = typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = colors.onSurface,
        )

        ElevatedCard(
            shape = StyleDefaults.cardShape,
            colors = CardDefaults.elevatedCardColors(containerColor = colors.surfaceContainerLow),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(vertical = 4.dp),
            ) {
                ingredients.forEachIndexed { index, (name, measure, image) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                    ) {
                        if (image != null) {
                            Image(
                                imageUi = image,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(64.dp),
                            )
                            Spacer(Modifier.width(16.dp))
                        }

                        Text(
                            text = name,
                            style = typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = colors.onSurface,
                            modifier = Modifier.weight(1f),
                        )

                        measure?.let {
                            if (it.isNotBlank()) {
                                Text(
                                    text = it,
                                    style = typography.bodyMedium,
                                    color = colors.onSurfaceVariant,
                                )
                            }
                        }
                    }
                    if (index < ingredients.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = colors.outlineVariant.copy(alpha = 0.5f),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DrinkIngredientsPreview() {
    DrinkIngredients(
        ingredients = IngredientUiModel.sample(4),
    )
}
