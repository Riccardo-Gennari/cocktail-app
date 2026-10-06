package it.cocktail.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import it.cocktail.data.datasource.network.client.KtorCocktailNetworkClient
import it.cocktail.data.repository.DefaultDrinkSummaryRepository
import it.cocktail.domain.usecase.GetDrinksUseCase
import it.cocktail.feature.list.DrinkListScreen
import it.cocktail.feature.list.DrinkListViewModel

@Composable
fun App(modifier: Modifier = Modifier) {
    DrinkListScreen(
        viewModel = viewModel {
            DrinkListViewModel(
                getDrinks = GetDrinksUseCase(
                    DefaultDrinkSummaryRepository(
                        KtorCocktailNetworkClient(),
                    ),
                ),
            )
        },
        modifier = modifier,
    )
}
