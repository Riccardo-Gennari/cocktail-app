package it.cocktail.core.ui.model

import io.ktor.client.plugins.HttpRequestTimeoutException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Represent different error types that ui can handle.
 *
 * Each [UiError] could trigger a different ui behavior (e.g. a different message to the user)
 */
enum class UiError {
    Generic,
    NoConnection,
}

fun Throwable.toUiError(): UiError {
    // can't use a simple 'when' because we need to traverse all causes,
    // since network error could be nested inside a different throwable
    var current: Throwable? = this
    while (current != null) {
        when (current) {
            is UnknownHostException,
            is ConnectException,
            is SocketException,
            is SocketTimeoutException,
            is HttpRequestTimeoutException,
            is IOException,
            -> return UiError.NoConnection
        }
        current = current.cause
    }
    return UiError.Generic
}
