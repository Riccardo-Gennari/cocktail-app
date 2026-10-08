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

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class NavigationModule {
    @Binds
    @ActivityRetainedScoped
    abstract fun bindDrinkDetailNavigator(impl: Nav3Navigator): DrinkDetailNavigator

    @Binds
    @ActivityRetainedScoped
    abstract fun bindDrinkListNavigator(impl: Nav3Navigator): DrinkListNavigator

    companion object {
        @Provides
        @ActivityRetainedScoped
        @Named("initialBackstack")
        fun provideInitialBackstack(): NavBackStack<NavKey> = NavBackStack(DrinkListRoute)
    }
}
