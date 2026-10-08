package it.cocktail.ui.detail.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class DrinkDetailRoute(
    val drinkId: Int,
) : NavKey
