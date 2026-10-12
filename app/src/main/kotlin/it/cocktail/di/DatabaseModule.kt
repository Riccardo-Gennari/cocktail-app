package it.cocktail.di

import android.content.Context
import androidx.room3.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.cocktail.data.datasource.local.PersistentStorage
import it.cocktail.data.datasource.local.room.DrinksDao
import it.cocktail.data.datasource.local.room.DrinksDatabase
import it.cocktail.data.datasource.local.room.RoomPersistentStorage
import javax.inject.Singleton

/**
 * Hilt module providing instances of local database data sources.
 * Installed in [SingletonComponent] to persist across the whole app lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {
    /**
     * Binds [RoomPersistentStorage] to the [PersistentStorage] interface.
     *
     * @param impl The [RoomPersistentStorage] implementation.
     * @return The [PersistentStorage] abstraction.
     */
    @Binds
    @Singleton
    abstract fun bindPersistentStorage(impl: RoomPersistentStorage): PersistentStorage

    companion object {
        /**
         * Provides the singleton instance of [DrinksDatabase].
         *
         * @param context The application [Context].
         * @return The [DrinksDatabase] instance.
         */
        @Provides
        @Singleton
        fun provideDrinksDatabase(
            @ApplicationContext context: Context,
        ): DrinksDatabase =
            Room
                .databaseBuilder(
                    context,
                    DrinksDatabase::class.java,
                    DrinksDatabase.DATABASE_NAME,
                ).build()

        /**
         * Provides the singleton [DrinksDao] from [DrinksDatabase].
         *
         * @param database The [DrinksDatabase] instance.
         * @return The [DrinksDao] instance.
         */
        @Provides
        @Singleton
        fun provideDrinksDao(database: DrinksDatabase): DrinksDao = database.drinksDao()
    }
}
