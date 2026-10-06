package it.cocktail.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.cocktail.BuildConfig
import it.cocktail.data.datasource.network.client.CocktailNetworkClient
import it.cocktail.data.datasource.network.client.KtorCocktailNetworkClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindCocktailNetworkClient(impl: KtorCocktailNetworkClient): CocktailNetworkClient

    companion object {
        @Provides
        @Named("cocktailApiKey")
        fun provideCocktailApiKey(): String = BuildConfig.COCKTAIL_API_KEY
    }
}
