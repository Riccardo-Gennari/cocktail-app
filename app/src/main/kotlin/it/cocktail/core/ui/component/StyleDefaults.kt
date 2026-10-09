package it.cocktail.core.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

object StyleDefaults {
    val cardShape = RoundedCornerShape(16.dp)

    val topAppBarTitleStyle @Composable get() = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)

    val topAppBarColors @Composable get() = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
    )
}
