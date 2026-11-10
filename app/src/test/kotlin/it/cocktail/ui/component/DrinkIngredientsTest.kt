package it.cocktail.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import it.cocktail.core.ui.component.AppTheme
import it.cocktail.ui.detail.component.DrinkIngredients
import it.cocktail.ui.detail.model.IngredientUiModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [34])
class DrinkIngredientsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `displays ingredients title and items`() {
        val ingredients = listOf(
            IngredientUiModel(name = "Rum", measure = "2 oz"),
            IngredientUiModel(name = "Lime", measure = "1"),
            IngredientUiModel(name = "Sugar", measure = null),
        )

        composeTestRule.setContent {
            AppTheme {
                DrinkIngredients(ingredients = ingredients)
            }
        }

        composeTestRule.onNodeWithText("Ingredients").assertIsDisplayed()
        composeTestRule.onNodeWithText("Rum").assertIsDisplayed()
        composeTestRule.onNodeWithText("2 oz").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lime").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sugar").assertIsDisplayed()
    }
}
