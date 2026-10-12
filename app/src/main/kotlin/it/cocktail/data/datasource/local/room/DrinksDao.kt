package it.cocktail.data.datasource.local.room

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import it.cocktail.data.datasource.local.dto.DrinkDetailLocalDto
import it.cocktail.data.datasource.local.dto.DrinkSummaryLocalDto

/** DAO for drink-related entities in the local database. */
@Dao
interface DrinksDao {
    /** Returns a list of all stored [DrinkSummaryLocalDto] */
    @Query("SELECT * FROM drink_summaries")
    suspend fun getDrinkSummaries(): List<DrinkSummaryLocalDto>

    /** Inserts [summaries] into storage, replacing any conflicts. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinkSummaries(summaries: List<DrinkSummaryLocalDto>)

    /** Deletes all stored [DrinkSummaryLocalDto] */
    @Query("DELETE FROM drink_summaries")
    suspend fun clearDrinkSummaries()

    /** Deletes all [DrinkSummaryLocalDto] from storage, and then inserts [summaries], as a single transaction. */
    @Transaction
    suspend fun replaceDrinkSummaries(summaries: List<DrinkSummaryLocalDto>) {
        clearDrinkSummaries()
        insertDrinkSummaries(summaries)
    }

    /** Returns stored [DrinkDetailLocalDto] matching [id], or null if none was found. */
    @Query("SELECT * FROM drink_details WHERE id = :id")
    suspend fun getDrinkDetail(id: Int): DrinkDetailLocalDto?

    /** Inserts [detail] into storage, replacing any conflicts. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinkDetail(detail: DrinkDetailLocalDto)
}
