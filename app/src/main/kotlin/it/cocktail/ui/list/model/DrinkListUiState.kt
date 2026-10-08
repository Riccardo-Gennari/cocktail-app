package it.cocktail.ui.list.model

import androidx.compose.runtime.Immutable

@Immutable
data class DrinkListUiState(
    val drinkList: List<DrinkSummaryUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val message: String? = null,
    val searchState: SearchState = SearchState(),
)
