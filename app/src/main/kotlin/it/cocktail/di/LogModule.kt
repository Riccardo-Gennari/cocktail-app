package it.cocktail.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.cocktail.core.common.log.DefaultLogger
import it.cocktail.core.common.log.Logger
import javax.inject.Singleton

/**
 * Hilt module providing instances related to logging.
 * Installed in [SingletonComponent] to persist across the whole app lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class LogModule {
    /**
     * Binds [DefaultLogger] to the [Logger] interface.
     *
     * @param impl The [DefaultLogger] implementation.
     * @return The [Logger] abstraction.
     */
    @Binds
    @Singleton
    abstract fun bindLogger(impl: DefaultLogger): Logger
}
