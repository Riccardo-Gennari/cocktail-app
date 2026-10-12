package it.cocktail.core.common.log

/**
 * Provides logging features for the app.
 */
interface Logger {
    fun debug(
        tag: String,
        message: String,
        exception: Throwable? = null,
    )

    fun error(
        tag: String,
        message: String,
        exception: Throwable? = null,
    )

    fun info(
        tag: String,
        message: String,
        exception: Throwable? = null,
    )

    fun warning(
        tag: String,
        message: String,
        exception: Throwable? = null,
    )
}
