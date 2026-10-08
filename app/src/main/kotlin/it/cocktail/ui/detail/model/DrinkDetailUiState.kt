package it.cocktail.ui.detail.model

import androidx.compose.runtime.Immutable

@Immutable
data class DrinkDetailUiState(
    val drinkDetail: DrinkDetailUiModel? = null,
    val isLoading: Boolean = false,
    val message: String? = null,
)
