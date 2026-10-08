package it.cocktail.ui.list.model

import androidx.compose.runtime.Immutable

@Immutable
data class SearchState(
    val query: String = "",
    val type: SearchType = SearchType.NAME,
)
