package it.cocktail.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.cocktail.core.ui.util.LoadingStateManager
import it.cocktail.core.ui.util.launchWhileLoading
import it.cocktail.domain.usecase.GetDrinksUseCase
import it.cocktail.feature.list.mapper.toDrinkQuery
import it.cocktail.feature.list.mapper.toUi
import it.cocktail.feature.list.model.DrinkSummaryUiModel
import it.cocktail.feature.list.model.SearchState
import it.cocktail.feature.list.model.SearchType
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
        private val getDrinks: GetDrinksUseCase,
    ) : ViewModel() {
        private val drinksFlow = MutableStateFlow<List<DrinkSummaryUiModel>>(emptyList())
        private val loadingState = LoadingStateManager()
        private val messageFlow = MutableStateFlow<String?>(null)
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

        fun onMessageShown() {
            messageFlow.value = null
        }

        fun onSearch() {
            viewModelScope.launchWhileLoading(loadingState) {
                getDrinks(query = uiState.value.searchState.toDrinkQuery()).fold(
                    onSuccess = { drinksFlow.value = it.map { drink -> drink.toUi() } },
                    onFailure = {
                        messageFlow.value = "Error: ${it.message}"
                        drinksFlow.value = emptyList()
                    },
                )
            }
        }

        fun onSearchTypeChanged(searchType: SearchType) {
            searchStateFlow.value = SearchState(query = "", type = searchType)
        }

        fun onSearchQueryChanged(query: String) {
            searchStateFlow.update { it.copy(query = query) }
        }
    }
