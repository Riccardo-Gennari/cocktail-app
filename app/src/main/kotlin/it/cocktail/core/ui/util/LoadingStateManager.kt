package it.cocktail.core.ui.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.decrementAndFetch
import kotlin.concurrent.atomics.incrementAndFetch

/**
 * Utility class that coordinates loading state of a component, where multiple concurrent loading operations are possible.
 *
 * [isLoading] is true when at least one loading operation is running.
 *
 * Each loading operation must be manually started using [onLoadingStarted], and stopped by calling [onLoadingStopped].
 */
@OptIn(ExperimentalAtomicApi::class)
class LoadingStateManager {
    private val isLoadingCount = AtomicInt(0)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun onLoadingStarted() = updateIsLoading(isLoadingCount.incrementAndFetch())

    fun onLoadingStopped() = updateIsLoading(isLoadingCount.decrementAndFetch())

    private fun updateIsLoading(currentCount: Int) = _isLoading.update { currentCount > 0 }
}
