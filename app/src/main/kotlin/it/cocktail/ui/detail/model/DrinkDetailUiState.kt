package it.cocktail.ui.detail.model

import androidx.compose.runtime.Immutable
import it.cocktail.core.ui.model.UiError
import it.cocktail.ui.detail.DrinkDetailScreen

/**
 * Represents the layout content state for [DrinkDetailScreen].
 */
enum class DetailContentState {
    Error,
    Loading,
    Content,
}

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

    val isRefreshing: Boolean = isLoading && (drinkDetail != null)

    val contentState: DetailContentState
        get() = when {
            error != null -> DetailContentState.Error
            drinkDetail == null -> DetailContentState.Loading
            else -> DetailContentState.Content
        }
}
