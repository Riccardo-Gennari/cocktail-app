package it.cocktail.data.datasource.local.dto

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import it.cocktail.domain.model.DrinkSummary
import kotlinx.serialization.Serializable

/**
 * Local storage DTO of domain entity [DrinkSummary]
 */
@Serializable
@Entity(tableName = "drink_summaries")
data class DrinkSummaryLocalDto(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "image_uri")
    val imageUri: String,
)
