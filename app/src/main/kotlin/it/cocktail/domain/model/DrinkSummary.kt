package it.cocktail.domain.model

/**
 * Domain model representing the summarized info of a cocktail.
 *
 * @property id UID for the drink.
 * @property name Name of the cocktail.
 * @property imageUri URI pointing to the drink image.
 */
data class DrinkSummary(
    val id: Int = 0,
    val name: String = "",
    val imageUri: String = "",
)
