package it.cocktail.ui.list.model

import androidx.compose.runtime.Immutable
import it.cocktail.core.ui.model.UiError

@Immutable
data class DrinkListUiState(
    val drinkList: List<DrinkSummaryUiModel> = emptyList(),
    val error: UiError? = null,
    val isLoading: Boolean = false,
    val searchState: SearchState = SearchState(),
) {
    val isError: Boolean = error != null
}
