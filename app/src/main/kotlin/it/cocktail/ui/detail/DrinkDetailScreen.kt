package it.cocktail.ui.detail

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.cocktail.R
import it.cocktail.core.ui.component.PullToRefreshBoxWithLoadingOverlay
import it.cocktail.core.ui.component.StyleDefaults
import it.cocktail.core.ui.model.UiError
import it.cocktail.ui.detail.component.DrinkCoverImage
import it.cocktail.ui.detail.component.DrinkDetailInfoChips
import it.cocktail.ui.detail.component.DrinkErrorState
import it.cocktail.ui.detail.component.DrinkIngredients
import it.cocktail.ui.detail.component.DrinkInstructions
import it.cocktail.ui.detail.model.DetailContentState
import it.cocktail.ui.detail.model.DrinkDetailUiModel
import it.cocktail.ui.detail.model.DrinkDetailUiState

/**
 * Entry point for the cocktail detail screen, wiring [DrinkDetailViewModel] state to [DrinkDetailScreen].
 */
@Composable
fun DrinkDetailScreen(
    viewModel: DrinkDetailViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DrinkDetailScreen(
        uiState = uiState,
        modifier = modifier,
        onNavigateBack = viewModel::onNavigateBack,
        onRefresh = viewModel::onRefresh,
    )
}

/**
 * Screen for displaying cocktail recipe details based on [uiState].
 *
 * @param uiState The current [DrinkDetailUiState] snapshot.
 * @param onNavigateBack is invoked when the user clicks on the header back button, or via gesture.
 * @param onRefresh is invoked when a pull-to-refresh action is triggered.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkDetailScreen(
    uiState: DrinkDetailUiState,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onRefresh: () -> Unit = {},
) {
    BackHandler(onBack = onNavigateBack)

    PullToRefreshBoxWithLoadingOverlay(
        isRefreshing = uiState.isRefreshing,
        onRefresh = onRefresh,
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = uiState.drinkDetail?.name.orEmpty(),
                            style = StyleDefaults.topAppBarTitleStyle,
                        )
                    },
                    colors = StyleDefaults.topAppBarColors,
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(painterResource(R.drawable.baseline_chevron_left_24), null)
                        }
                    },
                )
            },
        ) { scaffoldPadding ->
            when (uiState.contentState) {
                DetailContentState.Error -> {
                    uiState.error?.let { error ->
                        Box(
                            contentAlignment = Alignment.TopCenter,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(scaffoldPadding)
                                .verticalScroll(rememberScrollState()),
                        ) {
                            DrinkErrorState(
                                error = error,
                                onRetry = onRefresh,
                            )
                        }
                    }
                }

                DetailContentState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(scaffoldPadding),
                    ) {
                        CircularProgressIndicator()
                    }
                }

                DetailContentState.Content -> {
                    uiState.drinkDetail?.let { drink ->
                        DrinkDetailContent(
                            drink = drink,
                            modifier = Modifier.padding(scaffoldPadding),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DrinkDetailContent(
    drink: DrinkDetailUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (drink.imageUri != null) {
                DrinkCoverImage(drink.imageUri)
            }
            DrinkDetailInfoChips(drink.tags)
        }

        if (drink.ingredients.isNotEmpty()) {
            DrinkIngredients(drink.ingredients)
        }

        if (drink.instructions.isNotBlank()) {
            DrinkInstructions(drink.instructions)
        }
    }
}

@SuppressLint("VisibleForTests")
@PreviewScreenSizes
@Composable
private fun DrinkDetailScreenPreview() {
    DrinkDetailScreen(
        uiState = DrinkDetailUiState(drinkDetail = DrinkDetailUiModel.sample()),
    )
}

@Preview
@Composable
private fun DrinkDetailScreenLoadingPreview() {
    DrinkDetailScreen(
        uiState = DrinkDetailUiState(isLoading = true),
    )
}

@Preview
@Composable
private fun DrinkDetailScreenErrorPreview() {
    DrinkDetailScreen(
        uiState = DrinkDetailUiState(error = UiError.Generic),
    )
}

@Preview
@Composable
private fun DrinkDetailScreenNoConnectionPreview() {
    DrinkDetailScreen(
        uiState = DrinkDetailUiState(error = UiError.NoConnection),
    )
}
