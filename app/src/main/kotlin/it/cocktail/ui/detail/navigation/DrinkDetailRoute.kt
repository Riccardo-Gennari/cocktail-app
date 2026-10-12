package it.cocktail.ui.detail.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Navigation key for the cocktail detail feature.
 * @property drinkId The ID of the cocktail recipe to display.
 */
@Serializable
data class DrinkDetailRoute(
    val drinkId: Int,
) : NavKey
