package it.cocktail.domain.model

data class DrinkDetail(
    val id: Int,
    val name: String = "",
    val category: String = "",
    val alcoholic: String = "",
    val glass: String = "",
    val instructions: String = "",
    val imageUri: String = "",
    val ingredients: List<Ingredient> = emptyList(),
)
