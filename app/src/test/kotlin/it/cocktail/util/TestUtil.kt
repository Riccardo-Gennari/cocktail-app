package it.cocktail.util

import app.cash.turbine.ReceiveTurbine

suspend fun <T> ReceiveTurbine<T>.awaitUntil(predicate: (T) -> Boolean): T {
    while (true) awaitItem().let { if (predicate(it)) return it }
}
