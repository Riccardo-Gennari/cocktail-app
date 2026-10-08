package it.cocktail.data.datasource.local.dto

import kotlinx.serialization.Serializable

@Serializable
data class IngredientLocalDto(
    val name: String,
    val measure: String? = null,
    val imageUri: String,
)
