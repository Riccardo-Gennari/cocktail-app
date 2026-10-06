package it.cocktail.feature.list.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.cocktail.R
import it.cocktail.core.ui.component.Image
import it.cocktail.feature.list.model.DrinkSummaryUiModel

@Composable
fun DrinkSummaryListItem(
    drink: DrinkSummaryUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val cardShape = RoundedCornerShape(16.dp)
    val imageShape = RoundedCornerShape(12.dp)
    val colors = MaterialTheme.colorScheme

    ElevatedCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = cardShape,
        colors = CardDefaults.elevatedCardColors(containerColor = colors.surfaceContainerLow),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            if (drink.imageUri != null) {
                Image(
                    imageUi = drink.imageUri,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(imageShape),
                )
                Spacer(Modifier.width(16.dp))
            }

            Text(
                text = drink.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = colors.onSurface,
                modifier = Modifier.weight(1f),
            )

            Icon(
                painter = painterResource(R.drawable.baseline_chevron_right_24),
                contentDescription = null,
                tint = colors.onSurfaceVariant,
            )
        }
    }
}

@Preview
@Composable
private fun DrinkSummaryListItemPreview() {
    DrinkSummaryListItem(
        DrinkSummaryUiModel.sample(1).first(),
    )
}
