package it.cocktail.core.ui.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import dagger.hilt.android.scopes.ActivityRetainedScoped
import it.cocktail.ui.detail.navigation.DrinkDetailNavigator
import it.cocktail.ui.detail.navigation.DrinkDetailRoute
import it.cocktail.ui.list.navigation.DrinkListNavigator
import javax.inject.Inject
import javax.inject.Named

/**
 * The global navigation manager, providing navigation for all app features through interface composition.
 *
 * @param backStack the navigation backstack. Should be wired to the app's [NavDisplay].
 * Manual edits to the backstack are discouraged - use [Nav3Navigator] instead.
 */
@ActivityRetainedScoped
class Nav3Navigator
    @Inject
    constructor(
        @Named("navBackstack") val backStack: NavBackStack<NavKey>,
    ) : DrinkDetailNavigator,
        DrinkListNavigator {
        override fun navigateBack() {
            if (backStack.size <= 1) return
            backStack.removeLastOrNull()
        }

        override fun navigateToDetail(drinkId: Int) {
            backStack.add(DrinkDetailRoute(drinkId = drinkId))
        }
    }
