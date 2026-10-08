package it.cocktail.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.cocktail.R
import it.cocktail.core.ui.component.LoadingOverlay
import it.cocktail.core.ui.component.SnackBarEffect
import it.cocktail.ui.list.component.DrinkEmptyState
import it.cocktail.ui.list.component.DrinkSearchBarSection
import it.cocktail.ui.list.component.DrinkSummaryListItem
import it.cocktail.ui.list.model.DrinkListUiState
import it.cocktail.ui.list.model.DrinkSummaryUiModel
import it.cocktail.ui.list.model.SearchType

@Composable
fun DrinkListScreen(
    modifier: Modifier = Modifier,
    viewModel: DrinkListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DrinkListScreen(
        uiState = uiState,
        modifier = modifier,
        onDrinkClicked = viewModel::onDetailSelected,
        onMessageShown = viewModel::onMessageShown,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onSearchTypeChanged = viewModel::onSearchTypeChanged,
        onSearch = viewModel::onSearch,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkListScreen(
    uiState: DrinkListUiState,
    modifier: Modifier = Modifier,
    onDrinkClicked: (DrinkSummaryUiModel) -> Unit = {},
    onMessageShown: () -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {},
    onSearchTypeChanged: (SearchType) -> Unit = {},
    onSearch: () -> Unit = {},
) {
    val snackBarState = remember { SnackbarHostState() }

    SnackBarEffect(snackBarState, uiState.message, onMessageShown)

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarState) },
    ) { scaffoldPadding ->
        LoadingOverlay(
            uiState.isLoading,
            modifier = Modifier.padding(scaffoldPadding),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                DrinkSearchBarSection(
                    searchState = uiState.searchState,
                    onQueryChange = onSearchQueryChanged,
                    onSearchTypeChange = onSearchTypeChanged,
                    onSearch = onSearch,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                )

                if (uiState.drinkList.isEmpty() && !uiState.isLoading) {
                    DrinkEmptyState(modifier = Modifier.padding(top = 16.dp))
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    ) {
                        items(items = uiState.drinkList, key = { it.id }) { drink ->
                            DrinkSummaryListItem(drink, modifier = Modifier.animateItem()) {
                                onDrinkClicked(drink)
                            }
                        }
                    }
                }
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun DrinkListScreenPreview() {
    DrinkListScreen(
        uiState = DrinkListUiState(
            drinkList = DrinkSummaryUiModel.sample(6),
        ),
    )
}

@Preview
@Composable
private fun DrinkListScreenLoadingPreview() {
    DrinkListScreen(
        uiState = DrinkListUiState(
            isLoading = true,
            drinkList = DrinkSummaryUiModel.sample(6),
        ),
    )
}

@Preview
@Composable
private fun DrinkListScreenEmptyPreview() {
    DrinkListScreen(
        uiState = DrinkListUiState(
            drinkList = emptyList(),
        ),
    )
}
