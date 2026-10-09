package it.cocktail.core.common.log

import kotlin.reflect.KClass

fun KClass<*>.logTag() = this.simpleName.orEmpty()
