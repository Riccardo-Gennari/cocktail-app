package it.cocktail.core.common.log

import kotlin.reflect.KClass

/**
 * Returns the class simpleName. Useful for logging.
 */
fun KClass<*>.logTag() = this.simpleName.orEmpty()
