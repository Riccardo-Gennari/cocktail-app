package it.cocktail.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.cocktail.data.repository.DefaultDrinkDetailRepository
import it.cocktail.data.repository.DefaultDrinkSummaryRepository
import it.cocktail.domain.repository.DrinkDetailRepository
import it.cocktail.domain.repository.DrinkSummaryRepository
import javax.inject.Singleton

/**
 * Hilt module providing the repository instances.
 * Installed in [SingletonComponent] to persist across the whole app lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    /**
     * Binds [DefaultDrinkDetailRepository] to the [DrinkSummaryRepository] interface.
     *
     * @param impl The [DefaultDrinkDetailRepository] implementation.
     * @return The [DrinkSummaryRepository] abstraction.
     */
    @Binds
    @Singleton
    abstract fun bindDrinkDetailRepository(impl: DefaultDrinkDetailRepository): DrinkDetailRepository

    /**
     * Binds [DefaultDrinkSummaryRepository] to the [DrinkSummaryRepository] interface.
     *
     * @param impl The [DefaultDrinkSummaryRepository] implementation.
     * @return The [DrinkSummaryRepository] abstraction.
     */
    @Binds
    @Singleton
    abstract fun bindDrinkSummaryRepository(impl: DefaultDrinkSummaryRepository): DrinkSummaryRepository
}
