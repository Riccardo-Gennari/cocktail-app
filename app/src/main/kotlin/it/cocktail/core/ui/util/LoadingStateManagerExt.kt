package it.cocktail.core.ui.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

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
