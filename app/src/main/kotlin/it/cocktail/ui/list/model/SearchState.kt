package it.cocktail.ui.list.model

import androidx.compose.runtime.Immutable

/**
 * Ui model representing the state of a search component.
 */
@Immutable
data class SearchState(
    val query: String = "",
    val type: SearchType = SearchType.Name,
)
