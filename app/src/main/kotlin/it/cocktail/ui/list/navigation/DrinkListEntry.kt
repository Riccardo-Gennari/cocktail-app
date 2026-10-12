package it.cocktail.ui.list.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import it.cocktail.ui.list.DrinkListScreen

/** Registers [DrinkListScreen] into the app's navigation tree, with [DrinkListRoute] as key. */
fun EntryProviderScope<NavKey>.drinkList() {
    entry<DrinkListRoute> {
        DrinkListScreen()
    }
}
