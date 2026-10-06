package it.cocktail.feature.list

import androidx.compose.runtime.Stable
import it.cocktail.feature.list.model.DrinkSummaryUiModel
import it.cocktail.feature.list.model.SearchState

@Stable
data class DrinkListUiState(
    val drinkList: List<DrinkSummaryUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val message: String? = null,
    val searchState: SearchState = SearchState(),
)
