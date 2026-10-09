package it.cocktail.ui.detail.model

import androidx.compose.runtime.Immutable
import it.cocktail.core.ui.model.UiError

@Immutable
data class DrinkDetailUiState(
    val drinkDetail: DrinkDetailUiModel? = null,
    val error: UiError? = null,
    val isLoading: Boolean = false,
) {
    val isError: Boolean = error != null
}
