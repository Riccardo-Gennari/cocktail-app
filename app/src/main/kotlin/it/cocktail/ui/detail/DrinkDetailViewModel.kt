package it.cocktail.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import it.cocktail.core.common.log.Logger
import it.cocktail.core.common.log.logTag
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

@HiltViewModel(assistedFactory = DrinkDetailViewModel.Factory::class)
class DrinkDetailViewModel
    @AssistedInject
    constructor(
        @Assisted private val drinkId: Int,
        private val getDetail: GetDrinkDetailUseCase,
        private val log: Logger,
        private val navigator: DrinkDetailNavigator,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(drinkId: Int): DrinkDetailViewModel
        }

        private val detailFlow = MutableStateFlow<DrinkDetailUiModel?>(null)
        private val loadingState = LoadingStateManager()
        private val errorFlow = MutableStateFlow(false)

        val uiState: StateFlow<DrinkDetailUiState> = combine(
            detailFlow,
            loadingState.isLoading,
            errorFlow,
        ) { drink, isLoading, message ->
            DrinkDetailUiState(
                drinkDetail = drink,
                isLoading = isLoading,
                isError = message,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), DrinkDetailUiState())

        init {
            getDrinkDetail(drinkId)
        }

        fun onRefresh() {
            errorFlow.value = false
            getDrinkDetail(drinkId, forceRefresh = true)
        }

        fun onNavigateBack() = navigator.navigateBack()

        private fun getDrinkDetail(
            drinkId: Int,
            forceRefresh: Boolean = false,
        ) {
            viewModelScope.launchWhileLoading(loadingState) {
                getDetail(drinkId, forceRefresh).fold(
                    onSuccess = {
                        detailFlow.value = it?.toUi()
                        errorFlow.value = false
                    },
                    onFailure = {
                        log.debug(TAG, "Error fetching drink detail", it)
                        errorFlow.value = true
                        detailFlow.value = null
                    },
                )
            }
        }

        companion object {
            private val TAG = DrinkDetailViewModel::class.logTag()
        }
    }
