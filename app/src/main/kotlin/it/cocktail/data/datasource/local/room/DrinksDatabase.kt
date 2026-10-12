package it.cocktail.data.datasource.local.room

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import it.cocktail.data.datasource.local.dto.DrinkDetailLocalDto
import it.cocktail.data.datasource.local.dto.DrinkSummaryLocalDto

/** Room database for local storage of cocktail data. */
@Database(
    entities = [DrinkSummaryLocalDto::class, DrinkDetailLocalDto::class],
    version = 1,
)
@ColumnTypeConverters(DrinkTypeConverters::class)
abstract class DrinksDatabase : RoomDatabase() {
    /** DAO providing operations on drink-related entities */
    abstract fun drinksDao(): DrinksDao

    companion object {
        /** Filename of the SQLite database used by Room */
        const val DATABASE_NAME = "drinks_database"
    }
}
