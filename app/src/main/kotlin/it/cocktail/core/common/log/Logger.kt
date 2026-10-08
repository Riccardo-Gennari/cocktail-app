package it.cocktail.core.common.log

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
