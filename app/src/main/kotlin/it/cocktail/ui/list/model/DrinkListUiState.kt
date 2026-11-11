package it.cocktail.ui.list.model

import androidx.compose.runtime.Immutable
import it.cocktail.core.ui.model.UiError
import it.cocktail.ui.list.DrinkListScreen

/**
 * Represents the layout content state for [DrinkListScreen].
 */
enum class ListContentState {
    Error,
    Loading,
    Empty,
    Content,
}

/**
 * Ui model representing a state snapshot of [DrinkListScreen].
 */
@Immutable
data class DrinkListUiState(
    val drinkList: List<DrinkSummaryUiModel> = emptyList(),
    val error: UiError? = null,
    val isLoading: Boolean = false,
    val searchState: SearchState = SearchState(),
) {
    val isError: Boolean = error != null

    val isRefreshing: Boolean = isLoading && drinkList.isNotEmpty()

    val contentState: ListContentState
        get() = when {
            error != null -> ListContentState.Error
            isLoading && drinkList.isEmpty() -> ListContentState.Loading
            drinkList.isEmpty() -> ListContentState.Empty
            else -> ListContentState.Content
        }
}
