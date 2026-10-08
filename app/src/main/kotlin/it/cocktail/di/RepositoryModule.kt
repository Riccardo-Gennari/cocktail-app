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

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDrinkDetailRepository(impl: DefaultDrinkDetailRepository): DrinkDetailRepository

    @Binds
    @Singleton
    abstract fun bindDrinkSummaryRepository(impl: DefaultDrinkSummaryRepository): DrinkSummaryRepository
}
