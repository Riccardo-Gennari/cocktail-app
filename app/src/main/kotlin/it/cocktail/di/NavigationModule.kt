package it.cocktail.di

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import it.cocktail.core.ui.navigation.Nav3Navigator
import it.cocktail.ui.detail.navigation.DrinkDetailNavigator
import it.cocktail.ui.list.navigation.DrinkListNavigator
import it.cocktail.ui.list.navigation.DrinkListRoute
import javax.inject.Named

/**
 * Hilt module providing instances related to navigation.
 * Installed in [ActivityRetainedComponent] to follow the Activity lifecycle.
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class NavigationModule {
    /**
     * Binds [Nav3Navigator] to the [DrinkDetailNavigator] interface.
     *
     * @param impl The [Nav3Navigator] implementation.
     * @return The [DrinkDetailNavigator] abstraction.
     */
    @Binds
    @ActivityRetainedScoped
    abstract fun bindDrinkDetailNavigator(impl: Nav3Navigator): DrinkDetailNavigator

    /**
     * Binds [Nav3Navigator] to the [DrinkListNavigator] interface.
     *
     * @param impl The [Nav3Navigator] implementation.
     * @return The [DrinkListNavigator] abstraction.
     */
    @Binds
    @ActivityRetainedScoped
    abstract fun bindDrinkListNavigator(impl: Nav3Navigator): DrinkListNavigator

    companion object {
        /**
         * Provides the activity-scoped backstack used to coordinate navigation in the whole app.
         *
         * @return The [NavBackStack] instance.
         */
        @Provides
        @ActivityRetainedScoped
        @Named("navBackstack")
        fun provideNavBackstack(): NavBackStack<NavKey> = NavBackStack(DrinkListRoute)
    }
}
