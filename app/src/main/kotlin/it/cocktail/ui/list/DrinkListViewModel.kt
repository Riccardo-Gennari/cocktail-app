package it.cocktail.ui.list

import androidx.compose.material3.SnackbarResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.cocktail.R
import it.cocktail.core.common.log.Logger
import it.cocktail.core.ui.model.UiMessage
import it.cocktail.core.ui.model.UiMessageDuration
import it.cocktail.core.ui.model.UiText
import it.cocktail.core.ui.util.LoadingStateManager
import it.cocktail.core.ui.util.launchWhileLoading
import it.cocktail.domain.usecase.GetLastDrinksUseCase
import it.cocktail.domain.usecase.SearchDrinksUseCase
import it.cocktail.ui.list.mapper.toDrinkQuery
import it.cocktail.ui.list.mapper.toUi
import it.cocktail.ui.list.model.DrinkListUiState
import it.cocktail.ui.list.model.DrinkSummaryUiModel
import it.cocktail.ui.list.model.SearchState
import it.cocktail.ui.list.model.SearchType
import it.cocktail.ui.list.navigation.DrinkListNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DrinkListViewModel
    @Inject
    constructor(
        private val getLastDrinks: GetLastDrinksUseCase,
        private val log: Logger,
        private val navigator: DrinkListNavigator,
        private val searchDrinks: SearchDrinksUseCase,
    ) : ViewModel() {
        private val drinksFlow = MutableStateFlow<List<DrinkSummaryUiModel>>(emptyList())
        private val loadingState = LoadingStateManager()
        private val messageFlow = MutableStateFlow<UiMessage?>(null)
        private val searchStateFlow = MutableStateFlow(SearchState())

        val uiState: StateFlow<DrinkListUiState> = combine(
            drinksFlow,
            loadingState.isLoading,
            messageFlow,
            searchStateFlow,
        ) { drinks, isLoading, message, searchState ->
            DrinkListUiState(
                drinkList = drinks,
                isLoading = isLoading,
                message = message,
                searchState = searchState,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), DrinkListUiState())

        init {
            viewModelScope.launchWhileLoading(loadingState) {
                getLastDrinks().fold(
                    onSuccess = { drinks -> drinksFlow.value = drinks.map { it.toUi() } },
                    onFailure = { log.debug(TAG, "Failed to recover stored drinks", it) },
                )
            }
        }

        fun onSnackBarResult(result: SnackbarResult) {
            messageFlow.value = null
            if (result == SnackbarResult.ActionPerformed) {
                onSearch()
            }
        }

        fun onSearch() {
            viewModelScope.launchWhileLoading(loadingState) {
                searchDrinks(query = searchStateFlow.value.toDrinkQuery()).fold(
                    onSuccess = {
                        drinksFlow.value = it.map { drink -> drink.toUi() }
                        messageFlow.value = null
                    },
                    onFailure = {
                        log.debug(TAG, "Error fetching drink list", it)
                        messageFlow.value = UiMessage(
                            message = UiText.StringResource(resId = R.string.something_went_wrong),
                            actionLabel = UiText.StringResource(resId = R.string.retry),
                            duration = UiMessageDuration.Long,
                        )
                        drinksFlow.value = emptyList()
                    },
                )
            }
        }

        fun onSearchTypeChanged(searchType: SearchType) {
            if (searchStateFlow.value.type == searchType) return
            searchStateFlow.value = SearchState(query = "", type = searchType)
        }

        fun onSearchQueryChanged(query: String) {
            if (searchStateFlow.value.query == query) return
            searchStateFlow.update { it.copy(query = query) }
        }

        fun onDetailSelected(drink: DrinkSummaryUiModel) = navigator.navigateToDetail(drinkId = drink.id)

        companion object {
            private val TAG = DrinkListViewModel::class.simpleName.orEmpty()
        }
    }
