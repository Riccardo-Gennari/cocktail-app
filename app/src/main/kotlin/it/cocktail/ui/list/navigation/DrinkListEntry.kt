package it.cocktail.ui.list.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import it.cocktail.ui.list.DrinkListScreen

fun EntryProviderScope<NavKey>.drinkList() {
    entry<DrinkListRoute> {
        DrinkListScreen()
    }
}
