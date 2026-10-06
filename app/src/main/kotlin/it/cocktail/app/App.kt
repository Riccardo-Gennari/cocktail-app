package it.cocktail.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import it.cocktail.feature.list.DrinkListScreen

@Composable
fun App(modifier: Modifier = Modifier) {
    DrinkListScreen(
        modifier = modifier,
    )
}
