package it.cocktail.ui.list.model

import androidx.compose.runtime.Immutable

@Immutable
data class DrinkListUiState(
    val drinkList: List<DrinkSummaryUiModel> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val searchState: SearchState = SearchState(),
)
