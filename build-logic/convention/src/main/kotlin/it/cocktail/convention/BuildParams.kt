package it.cocktail.convention

import org.gradle.api.JavaVersion

object BuildParams {
    const val APP_NAME = "CocktailApp"
    const val COCKTAIL_API_KEY = "1"
    const val COMPILE_SDK = 37
    const val MIN_SDK = 32
    const val TARGET_SDK = 37
    const val PACKAGE_NAME = "it.cocktail"

    val javaVersion = JavaVersion.VERSION_21
}
