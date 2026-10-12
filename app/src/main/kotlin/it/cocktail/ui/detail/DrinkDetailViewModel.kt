package it.cocktail.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import it.cocktail.core.common.log.Logger
import it.cocktail.core.common.log.logTag
import it.cocktail.core.ui.model.UiError
import it.cocktail.core.ui.model.toUiError
import it.cocktail.core.ui.util.LoadingStateManager
import it.cocktail.core.ui.util.launchWhileLoading
import it.cocktail.domain.usecase.GetDrinkDetailUseCase
import it.cocktail.ui.detail.mapper.toUi
import it.cocktail.ui.detail.model.DrinkDetailUiModel
import it.cocktail.ui.detail.model.DrinkDetailUiState
import it.cocktail.ui.detail.navigation.DrinkDetailNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * [ViewModel] managing state and events for [DrinkDetailScreen].
 *
 * @param drinkId The ID of the cocktail recipe to display.
 * @param getDetail Use case to fetch the recipe data.
 * @param log [Logger] for recording events.
 * @param navigator Handles screen navigation events.
 */
@HiltViewModel(assistedFactory = DrinkDetailViewModel.Factory::class)
class DrinkDetailViewModel
    @AssistedInject
    constructor(
        @Assisted private val drinkId: Int,
        private val getDetail: GetDrinkDetailUseCase,
        private val log: Logger,
        private val navigator: DrinkDetailNavigator,
    ) : ViewModel() {
        /** Factory for Hilt assisted injection of [DrinkDetailViewModel]. Allows for typesafe injection of [drinkId] */
        @AssistedFactory
        interface Factory {
            fun create(drinkId: Int): DrinkDetailViewModel
        }

        private val detailFlow = MutableStateFlow<DrinkDetailUiModel?>(null)
        private val loadingState = LoadingStateManager()
        private val errorFlow = MutableStateFlow<UiError?>(null)

        /** Provides a flow of [DrinkDetailUiState] snapshots for the UI to display. */
        val uiState: StateFlow<DrinkDetailUiState> = combine(
            detailFlow,
            errorFlow,
            loadingState.isLoading,
        ) { drink, error, isLoading ->
            DrinkDetailUiState(
                drinkDetail = drink,
                error = error,
                isLoading = isLoading,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), DrinkDetailUiState())

        init {
            getDrinkDetail(drinkId)
        }

        /** Refreshes the current recipe. */
        fun onRefresh() {
            errorFlow.value = null
            getDrinkDetail(drinkId, forceRefresh = true)
        }

        /** Navigates back to the previous screen. */
        fun onNavigateBack() = navigator.navigateBack()

        private fun getDrinkDetail(
            drinkId: Int,
            forceRefresh: Boolean = false,
        ) {
            viewModelScope.launchWhileLoading(loadingState) {
                getDetail(drinkId, forceRefresh).fold(
                    onSuccess = {
                        detailFlow.value = it?.toUi()
                        errorFlow.value = null
                    },
                    onFailure = {
                        log.debug(TAG, "Error fetching drink detail", it)
                        errorFlow.value = it.toUiError()
                        detailFlow.value = null
                    },
                )
            }
        }

        companion object {
            private val TAG = DrinkDetailViewModel::class.logTag()
        }
    }
