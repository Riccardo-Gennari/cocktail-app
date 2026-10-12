package it.cocktail.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import it.cocktail.core.ui.component.AppTheme
import it.cocktail.ui.detail.navigation.drinkDetail
import it.cocktail.ui.list.navigation.drinkList

/**
 * Main Compose entry point for the whole application.
 * Navigation is orchestrated by [backStack].
 * @param backStack the app's [NavBackStack], which decides the currently visible content, and all other possible routes.
 */
@Composable
fun App(
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier,
) {
    AppTheme {
        NavDisplay(
            backStack = backStack,
            modifier = modifier,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                drinkDetail()
                drinkList()
            },
        )
    }
}
