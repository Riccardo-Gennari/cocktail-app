package it.cocktail.core.ui.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * a loading operation is started on [loadingState] as soon as the function is called.
 *
 * The operation is stopped after the coroutine ends, even if exceptions are thrown by [block].
 *
 * @param loadingState the [LoadingStateManager] where loading operations will be triggered
 */
fun CoroutineScope.launchWhileLoading(
    loadingState: LoadingStateManager,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend () -> Unit,
): Job {
    loadingState.onLoadingStarted()
    return launch(context, start) {
        try {
            block()
        } finally {
            loadingState.onLoadingStopped()
        }
    }
}
