package it.cocktail.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import it.cocktail.core.ui.component.AppTheme
import it.cocktail.ui.list.component.DrinkSummaryListItem
import it.cocktail.ui.list.model.DrinkSummaryUiModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [34])
class DrinkSummaryListItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `displays drink name correctly`() {
        val drink = DrinkSummaryUiModel(
            id = 1,
            name = "Mojito",
            imageUri = null,
        )

        composeTestRule.setContent {
            AppTheme {
                DrinkSummaryListItem(drink = drink)
            }
        }

        composeTestRule.onNodeWithText("Mojito").assertIsDisplayed()
    }

    @Test
    fun `triggers onClick callback when item is clicked`() {
        var clicked = false
        val drink = DrinkSummaryUiModel(
            id = 2,
            name = "Margarita",
            imageUri = null,
        )

        composeTestRule.setContent {
            AppTheme {
                DrinkSummaryListItem(
                    drink = drink,
                    onClick = { clicked = true },
                )
            }
        }

        composeTestRule.onNodeWithText("Margarita").performClick()
        clicked shouldBe true
    }
}
