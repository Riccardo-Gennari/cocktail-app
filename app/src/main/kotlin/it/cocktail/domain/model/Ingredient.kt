package it.cocktail.domain.model

/**
 * Domain model representing an ingredient used in the preparation of cocktails.
 *
 * @property name Name of the ingredient.
 * @property measure Measure of the ingredient required to prepare the cocktail.
 * @property imageUri URI pointing to the ingredient image.
 */
data class Ingredient(
    val name: String = "",
    val measure: String? = null,
    val imageUri: String = "",
)
