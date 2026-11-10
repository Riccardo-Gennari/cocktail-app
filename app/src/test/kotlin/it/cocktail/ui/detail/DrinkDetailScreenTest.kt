package it.cocktail.ui.detail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import it.cocktail.core.ui.component.AppTheme
import it.cocktail.ui.detail.model.DrinkDetailUiModel
import it.cocktail.ui.detail.model.DrinkDetailUiState
import it.cocktail.ui.detail.model.IngredientUiModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [34])
class DrinkDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `displays cocktail detail information correctly`() {
        val drink = DrinkDetailUiModel(
            id = 1,
            name = "Old Fashioned",
            instructions = "Stir ingredients with ice and strain into glass.",
            ingredients = listOf(
                IngredientUiModel(name = "Bourbon", measure = "2 oz"),
                IngredientUiModel(name = "Angostura bitters", measure = "2 dashes"),
            ),
            tags = listOf("Classic", "Alcoholic"),
        )
        val uiState = DrinkDetailUiState(drinkDetail = drink)

        composeTestRule.setContent {
            AppTheme {
                DrinkDetailScreen(uiState = uiState)
            }
        }

        composeTestRule.onNodeWithText("Old Fashioned").assertIsDisplayed()
        composeTestRule.onNodeWithText("Classic").assertIsDisplayed()
        composeTestRule.onNodeWithText("Alcoholic").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bourbon").assertIsDisplayed()
        composeTestRule.onNodeWithText("Stir ingredients with ice and strain into glass.").assertIsDisplayed()
    }
}
