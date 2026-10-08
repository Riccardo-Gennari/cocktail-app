package it.cocktail.ui.detail.model

import androidx.compose.runtime.Immutable
import it.cocktail.core.ui.model.UiMessage

@Immutable
data class DrinkDetailUiState(
    val drinkDetail: DrinkDetailUiModel? = null,
    val isLoading: Boolean = false,
    val message: UiMessage? = null,
)
