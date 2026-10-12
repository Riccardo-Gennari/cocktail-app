package it.cocktail.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom [Application] instance of the app.
 * Used by Hilt as entry point for DI.
 */
@HiltAndroidApp
class CocktailApplication : Application()
