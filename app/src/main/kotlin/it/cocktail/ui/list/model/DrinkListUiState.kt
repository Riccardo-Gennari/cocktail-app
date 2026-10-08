package it.cocktail.ui.list.model

import androidx.compose.runtime.Immutable
import it.cocktail.core.ui.model.UiMessage

@Immutable
data class DrinkListUiState(
    val drinkList: List<DrinkSummaryUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val message: UiMessage? = null,
    val searchState: SearchState = SearchState(),
)
