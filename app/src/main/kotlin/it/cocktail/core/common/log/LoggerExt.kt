package it.cocktail.core.common.log

fun Logger.exception(
    tag: String,
    exception: Throwable,
) = error(tag, exception.message ?: "Exception encountered", exception)
