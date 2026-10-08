package it.cocktail.data.datasource.local.room

import androidx.room3.ColumnTypeConverter
import it.cocktail.data.datasource.local.dto.IngredientLocalDto
import kotlinx.serialization.json.Json

class DrinkTypeConverters {
    @ColumnTypeConverter
    fun fromIngredientList(value: List<IngredientLocalDto>): String = Json.encodeToString(value)

    @ColumnTypeConverter
    fun toIngredientList(value: String): List<IngredientLocalDto> =
        if (value.isBlank()) emptyList() else Json.decodeFromString(value)
}
