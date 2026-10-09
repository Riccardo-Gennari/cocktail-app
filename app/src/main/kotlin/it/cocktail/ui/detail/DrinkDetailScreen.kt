package it.cocktail.ui.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.cocktail.R
import it.cocktail.core.ui.component.PullToRefreshBoxWithLoadingOverlay
import it.cocktail.ui.detail.component.DrinkCoverImage
import it.cocktail.ui.detail.component.DrinkDetailInfoChips
import it.cocktail.ui.detail.component.DrinkErrorState
import it.cocktail.ui.detail.component.DrinkIngredients
import it.cocktail.ui.detail.component.DrinkInstructions
import it.cocktail.ui.detail.model.DrinkDetailUiModel
import it.cocktail.ui.detail.model.DrinkDetailUiState

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkDetailScreen(
    uiState: DrinkDetailUiState,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onRefresh: () -> Unit = {},
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    BackHandler(onBack = onNavigateBack)

    PullToRefreshBoxWithLoadingOverlay(
        isLoading = uiState.isLoading,
        onRefresh = onRefresh,
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = uiState.drinkDetail?.name.orEmpty(),
                            style = typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = colors.surface),
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(painterResource(R.drawable.baseline_chevron_left_24), null)
                        }
                    },
                )
            },
        ) { scaffoldPadding ->
            when {
                uiState.isError -> {
                    DrinkErrorState(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(scaffoldPadding),
                        onRetry = onRefresh,
                    )
                }

                uiState.drinkDetail != null -> {
                    DrinkDetailContent(
                        drink = uiState.drinkDetail,
                        modifier = Modifier.padding(scaffoldPadding),
                    )
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
        uiState = DrinkDetailUiState(isError = true),
    )
}
