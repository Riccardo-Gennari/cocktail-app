package it.cocktail.domain.model

/**
 * Domain model representing the full recipe for a cocktail.
 *
 * @property id UID for the drink.
 * @property name Name of the cocktail.
 * @property category Category classification (e.g., "Cocktail", "Drink").
 * @property alcoholic Alcoholic classification (e.g., "Alcoholic", "Non-alcoholic").
 * @property glass Recommended type of glass for serving.
 * @property instructions Step-by-step preparation instructions.
 * @property imageUri URI pointing to the drink image.
 * @property ingredients List of [Ingredient] required for the cocktail.
 */
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
