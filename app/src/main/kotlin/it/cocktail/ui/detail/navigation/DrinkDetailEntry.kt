package it.cocktail.ui.detail.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import it.cocktail.ui.detail.DrinkDetailScreen
import it.cocktail.ui.detail.DrinkDetailViewModel

fun EntryProviderScope<NavKey>.drinkDetail() {
    entry<DrinkDetailRoute> { route ->
        DrinkDetailScreen(
            viewModel = hiltViewModel<DrinkDetailViewModel, DrinkDetailViewModel.Factory>(
                creationCallback = { factory -> factory.create(route.drinkId) },
            ),
        )
    }
}
