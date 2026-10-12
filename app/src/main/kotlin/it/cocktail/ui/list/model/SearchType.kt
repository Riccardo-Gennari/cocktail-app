package it.cocktail.ui.list.model

import androidx.compose.runtime.Immutable

/**
 * Ui model representing the query criteria of a search component.
 */
@Immutable
enum class SearchType {
    Name,
    Ingredient,
}
