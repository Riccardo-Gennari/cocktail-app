package it.cocktail.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import it.cocktail.core.ui.navigation.Nav3Navigator
import javax.inject.Inject

/**
 * The app's main (and only) activity.
 * Used by Hilt as entry point for DI.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Nav3Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(backStack = navigator.backStack)
        }
    }
}
