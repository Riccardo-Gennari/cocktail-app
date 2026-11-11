package it.cocktail.ui.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import it.cocktail.R
import it.cocktail.core.ui.component.PullToRefreshBoxWithLoadingOverlay
import it.cocktail.core.ui.component.StyleDefaults
import it.cocktail.core.ui.model.UiError
import it.cocktail.ui.detail.component.DrinkErrorState
import it.cocktail.ui.list.component.DrinkEmptyState
import it.cocktail.ui.list.component.DrinkSearchBarSection
import it.cocktail.ui.list.component.DrinkSummaryListItem
import it.cocktail.ui.list.model.DrinkListUiState
import it.cocktail.ui.list.model.DrinkSummaryUiModel
import it.cocktail.ui.list.model.SearchType

/**
 * Entry point for the cocktail search screen, wiring [DrinkListViewModel] state to [DrinkListScreen].
 */
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
        onRefresh = viewModel::onRefresh,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onSearchTypeChanged = viewModel::onSearchTypeChanged,
        onSearch = viewModel::onSearch,
    )
}

/**
 * Screen for searching and viewing cocktail lists based on [uiState].
 *
 * @param uiState The current [DrinkListUiState] snapshot.
 * @param onDrinkClicked is invoked when a cocktail item is selected.
 * @param onRefresh is invoked when a pull-to-refresh action is triggered.
 * @param onSearchQueryChanged is invoked when the user updates the search text. Search will auto-trigger after typing pauses.
 * @param onSearchTypeChanged is invoked when the [SearchType] chip changes.
 * @param onSearch is invoked when a search action is submitted.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkListScreen(
    uiState: DrinkListUiState,
    modifier: Modifier = Modifier,
    onDrinkClicked: (DrinkSummaryUiModel) -> Unit = {},
    onRefresh: () -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {},
    onSearchTypeChanged: (SearchType) -> Unit = {},
    onSearch: () -> Unit = {},
) {
    PullToRefreshBoxWithLoadingOverlay(
        isLoading = uiState.isLoading,
        onRefresh = onSearch,
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = StyleDefaults.topAppBarTitleStyle,
                        )
                    },
                    colors = StyleDefaults.topAppBarColors,
                )
            },
        ) { scaffoldPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding),
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

                val isEmptyState = remember(uiState.drinkList, uiState.isLoading) {
                    uiState.drinkList.isEmpty() && !uiState.isLoading
                }

                LazyColumn(
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth().weight(1f),
                ) {
                    when {
                        uiState.error != null -> {
                            item {
                                Box(
                                    contentAlignment = Alignment.TopCenter,
                                    modifier = Modifier.fillMaxWidth().animateItem(),
                                ) {
                                    DrinkErrorState(
                                        error = uiState.error,
                                        onRetry = onRefresh,
                                    )
                                }
                            }
                        }

                        isEmptyState -> {
                            item {
                                Box(
                                    contentAlignment = Alignment.TopCenter,
                                    modifier = Modifier.fillMaxWidth().animateItem(),
                                ) {
                                    DrinkEmptyState()
                                }
                            }
                        }

                        else -> {
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
}

@SuppressLint("VisibleForTests")
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

@Preview
@Composable
private fun DrinkListScreenErrorPreview() {
    DrinkListScreen(
        uiState = DrinkListUiState(
            error = UiError.Generic,
        ),
    )
}

@Preview
@Composable
private fun DrinkListScreenNoConnectionPreview() {
    DrinkListScreen(
        uiState = DrinkListUiState(
            error = UiError.NoConnection,
        ),
    )
}
