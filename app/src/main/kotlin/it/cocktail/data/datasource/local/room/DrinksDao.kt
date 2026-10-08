package it.cocktail.data.datasource.local.room

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import it.cocktail.data.datasource.local.dto.DrinkDetailLocalDto
import it.cocktail.data.datasource.local.dto.DrinkSummaryLocalDto

@Dao
interface DrinksDao {
    @Query("SELECT * FROM drink_summaries")
    suspend fun getDrinkSummaries(): List<DrinkSummaryLocalDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinkSummaries(summaries: List<DrinkSummaryLocalDto>)

    @Query("DELETE FROM drink_summaries")
    suspend fun clearDrinkSummaries()

    @Transaction
    suspend fun replaceDrinkSummaries(summaries: List<DrinkSummaryLocalDto>) {
        clearDrinkSummaries()
        insertDrinkSummaries(summaries)
    }

    @Query("SELECT * FROM drink_details WHERE id = :id")
    suspend fun getDrinkDetail(id: Int): DrinkDetailLocalDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinkDetail(detail: DrinkDetailLocalDto)
}
