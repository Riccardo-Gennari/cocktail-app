package it.cocktail.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.cocktail.core.common.log.DefaultLogger
import it.cocktail.core.common.log.Logger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LogModule {
    @Binds
    @Singleton
    abstract fun bindLogger(impl: DefaultLogger): Logger
}
