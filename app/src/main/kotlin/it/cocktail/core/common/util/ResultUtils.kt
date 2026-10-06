package it.cocktail.core.common.util

import kotlin.coroutines.cancellation.CancellationException

/**
 * Executes the given [block] and returns a [Result] containing the result of the block
 * execution, or a [Result.failure] if an exception occurred.
 *
 * Unlike standard [runCatching], if the block throws a [CancellationException],
 * it is rethrown to ensure coroutine cancellation is preserved.
 */
inline fun <T> runCatchingCancelable(block: () -> T): Result<T> =
    try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        Result.failure(e)
    }
