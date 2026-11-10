package it.cocktail.ui.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import it.cocktail.core.ui.component.AppTheme
import it.cocktail.core.ui.model.UiError
import it.cocktail.ui.list.model.DrinkListUiState
import it.cocktail.ui.list.model.DrinkSummaryUiModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [34])
class DrinkListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `displays list of drinks when state contains drinkList`() {
        val drinks = listOf(
            DrinkSummaryUiModel(id = 1, name = "Mojito"),
            DrinkSummaryUiModel(id = 2, name = "Daiquiri"),
        )
        val uiState = DrinkListUiState(drinkList = drinks)

        composeTestRule.setContent {
            AppTheme {
                DrinkListScreen(uiState = uiState)
            }
        }

        composeTestRule.onNodeWithText("Mojito").assertIsDisplayed()
        composeTestRule.onNodeWithText("Daiquiri").assertIsDisplayed()
    }

    @Test
    fun `displays empty state when list is empty and not loading`() {
        val uiState = DrinkListUiState(drinkList = emptyList(), isLoading = false)

        composeTestRule.setContent {
            AppTheme {
                DrinkListScreen(uiState = uiState)
            }
        }

        composeTestRule.onNodeWithText("No cocktails found").assertIsDisplayed()
    }

    @Test
    fun `displays error state when error is present`() {
        val uiState = DrinkListUiState(error = UiError.NoConnection)

        composeTestRule.setContent {
            AppTheme {
                DrinkListScreen(uiState = uiState)
            }
        }

        val errorMessage = "No internet connection.\nPlease check your network and try again."
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun `triggers onDrinkClicked when a drink item is tapped`() {
        var clickedDrink: DrinkSummaryUiModel? = null
        val drinks = listOf(
            DrinkSummaryUiModel(id = 1, name = "Piña Colada"),
        )
        val uiState = DrinkListUiState(drinkList = drinks)

        composeTestRule.setContent {
            AppTheme {
                DrinkListScreen(
                    uiState = uiState,
                    onDrinkClicked = { clickedDrink = it },
                )
            }
        }

        composeTestRule.onNodeWithText("Piña Colada").performClick()
        clickedDrink?.name shouldBe "Piña Colada"
    }
}
