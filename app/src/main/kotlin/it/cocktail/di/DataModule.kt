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

/**
 * Hilt module providing instances of network data sources.
 * Installed in [SingletonComponent] to persist across the whole app lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    /**
     * Binds [KtorCocktailNetworkClient] to the [CocktailNetworkClient] interface.
     *
     * @param impl The [CocktailNetworkClient] implementation.
     * @return The [KtorCocktailNetworkClient] abstraction.
     */
    @Binds
    @Singleton
    abstract fun bindCocktailNetworkClient(impl: KtorCocktailNetworkClient): CocktailNetworkClient

    companion object {
        /**
         * Provides the API key used to authorize network calls to CocktailDB.
         *
         * @return The [String] apiKey.
         */
        @Provides
        @Named("cocktailApiKey")
        fun provideCocktailApiKey(): String = BuildConfig.COCKTAIL_API_KEY
    }
}
