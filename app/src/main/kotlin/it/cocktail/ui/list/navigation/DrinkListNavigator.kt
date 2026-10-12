package it.cocktail.ui.list.navigation

/**
 * Provides navigation for the cocktail list feature.
 */
interface DrinkListNavigator {
    /**
     * Navigates to the recipe detail associated to the drink with id [drinkId].
     * @param drinkId The ID of the cocktail recipe to navigate to.
     */
    fun navigateToDetail(drinkId: Int)
}
