package it.cocktail.data.datasource.local.dto

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "drink_details")
data class DrinkDetailLocalDto(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "alcoholic")
    val alcoholic: String,
    @ColumnInfo(name = "glass")
    val glass: String,
    @ColumnInfo(name = "instructions")
    val instructions: String,
    @ColumnInfo(name = "image_uri")
    val imageUri: String,
    @ColumnInfo(name = "ingredients")
    val ingredients: List<IngredientLocalDto>,
)
