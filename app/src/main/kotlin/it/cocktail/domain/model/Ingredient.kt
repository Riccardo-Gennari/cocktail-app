package it.cocktail.domain.model

data class Ingredient(
    val name: String = "",
    val measure: String? = null,
    val imageUri: String = "",
)
