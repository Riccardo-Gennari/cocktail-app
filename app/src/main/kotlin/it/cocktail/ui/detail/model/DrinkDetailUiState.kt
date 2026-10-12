package it.cocktail.ui.detail.model

import androidx.compose.runtime.Immutable
import it.cocktail.core.ui.model.UiError
import it.cocktail.ui.detail.DrinkDetailScreen

/**
 * Ui model representing a state snapshot of [DrinkDetailScreen].
 */
@Immutable
data class DrinkDetailUiState(
    val drinkDetail: DrinkDetailUiModel? = null,
    val error: UiError? = null,
    val isLoading: Boolean = false,
) {
    val isError: Boolean = error != null
}
