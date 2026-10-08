package it.cocktail.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import it.cocktail.R
import it.cocktail.core.common.log.Logger
import it.cocktail.core.ui.model.UiMessage
import it.cocktail.core.ui.model.UiMessageDuration
import it.cocktail.core.ui.model.UiMessageResult
import it.cocktail.core.ui.model.UiText
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
        private val messageFlow = MutableStateFlow<UiMessage?>(null)

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
            getDrinkDetail(drinkId)
        }

        fun onMessageResult(result: UiMessageResult) {
            messageFlow.value = null
            if (result == UiMessageResult.ActionPerformed) {
                getDrinkDetail(drinkId)
            }
        }

        fun onNavigateBack() = navigator.navigateBack()

        private fun getDrinkDetail(drinkId: Int) {
            viewModelScope.launchWhileLoading(loadingState) {
                getDetail(drinkId).fold(
                    onSuccess = {
                        detailFlow.value = it?.toUi()
                        messageFlow.value = null
                    },
                    onFailure = {
                        log.debug(TAG, "Error fetching drink detail", it)
                        messageFlow.value = UiMessage(
                            message = UiText.StringResource(resId = R.string.something_went_wrong),
                            actionLabel = UiText.StringResource(resId = R.string.retry),
                            duration = UiMessageDuration.Long,
                        )
                        detailFlow.value = null
                    },
                )
            }
        }

        companion object {
            private val TAG = DrinkDetailViewModel::class.simpleName.orEmpty()
        }
    }
