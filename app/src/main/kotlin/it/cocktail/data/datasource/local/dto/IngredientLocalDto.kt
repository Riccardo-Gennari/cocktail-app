package it.cocktail.data.datasource.local.dto

import it.cocktail.domain.model.Ingredient
import kotlinx.serialization.Serializable

/**
 * Local storage DTO of domain entity [Ingredient]
 */
@Serializable
data class IngredientLocalDto(
    val name: String,
    val measure: String? = null,
    val imageUri: String,
)
