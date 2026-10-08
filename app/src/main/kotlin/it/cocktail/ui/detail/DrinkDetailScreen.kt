package it.cocktail.ui.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.cocktail.R
import it.cocktail.core.ui.component.LoadingOverlay
import it.cocktail.core.ui.component.SnackBarEffect
import it.cocktail.core.ui.model.UiMessageResult
import it.cocktail.ui.detail.component.DrinkCoverImage
import it.cocktail.ui.detail.component.DrinkDetailInfoChips
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
        onMessageResult = viewModel::onMessageResult,
        onNavigateBack = viewModel::onNavigateBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkDetailScreen(
    uiState: DrinkDetailUiState,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    onMessageResult: (UiMessageResult) -> Unit = {},
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val snackBarState = remember { SnackbarHostState() }

    BackHandler(onBack = onNavigateBack)
    SnackBarEffect(snackBarState, uiState.message?.asSnackBarVisuals(), onMessageResult)

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
        snackbarHost = {
            SnackbarHost(hostState = snackBarState) { snackBarData ->
                Snackbar(snackBarData)
            }
        },
    ) { scaffoldPadding ->
        LoadingOverlay(
            uiState.isLoading,
            modifier = Modifier.padding(scaffoldPadding),
        ) {
            uiState.drinkDetail?.let { drink ->
                DrinkDetailContent(drink = drink)
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
        uiState = DrinkDetailUiState(
            drinkDetail = DrinkDetailUiModel.sample(),
        ),
    )
}

@Preview
@Composable
private fun DrinkDetailScreenLoadingPreview() {
    DrinkDetailScreen(
        uiState = DrinkDetailUiState(
            isLoading = true,
            drinkDetail = null,
        ),
    )
}
