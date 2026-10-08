package it.cocktail.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
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
        private val navigator: DrinkDetailNavigator,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(drinkId: Int): DrinkDetailViewModel
        }

        private val detailFlow = MutableStateFlow<DrinkDetailUiModel?>(null)
        private val loadingState = LoadingStateManager()
        private val messageFlow = MutableStateFlow<String?>(null)

        val uiState: StateFlow<DrinkDetailUiState> = combine(
            detailFlow,
            loadingState.isLoading,
            messageFlow,
        ) { drink, isLoading, message ->
            DrinkDetailUiState(
                drinkDetail = drink,
                isLoading = isLoading,
                message = message,
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), DrinkDetailUiState())

        init {
            viewModelScope.launchWhileLoading(loadingState) {
                getDrinkDetail(drinkId)
            }
        }

        fun onMessageShown() {
            messageFlow.value = null
        }

        fun onNavigateBack() = navigator.navigateBack()

        private suspend fun getDrinkDetail(drinkId: Int) {
            getDetail(drinkId).fold(
                onSuccess = { detailFlow.value = it?.toUi() },
                onFailure = {
                    messageFlow.value = "Error: ${it.message}"
                    detailFlow.value = null
                },
            )
        }
    }
