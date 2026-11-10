package it.cocktail.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import it.cocktail.core.ui.component.AppTheme
import it.cocktail.ui.list.component.DrinkSearchBarSection
import it.cocktail.ui.list.model.SearchState
import it.cocktail.ui.list.model.SearchType
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [34])
class DrinkSearchBarSectionTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `displays query text and filter chips`() {
        val searchState = SearchState(
            query = "Mojito",
            type = SearchType.Name,
        )

        composeTestRule.setContent {
            AppTheme {
                DrinkSearchBarSection(
                    searchState = searchState,
                    onQueryChange = {},
                    onSearchTypeChange = {},
                    onSearch = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Mojito").assertIsDisplayed()
        composeTestRule.onNodeWithText("Name").assertIsDisplayed().assertIsSelected()
        composeTestRule.onNodeWithText("Ingredient").assertIsDisplayed()
    }

    @Test
    fun `triggers onQueryChange when typing text`() {
        var updatedQuery = ""
        val searchState = SearchState(
            query = "",
            type = SearchType.Name,
        )

        composeTestRule.setContent {
            AppTheme {
                DrinkSearchBarSection(
                    searchState = searchState,
                    onQueryChange = { updatedQuery = it },
                    onSearchTypeChange = {},
                    onSearch = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Search cocktail name…").performTextInput("Gin")
        updatedQuery shouldBe "Gin"
    }

    @Test
    fun `triggers onSearchTypeChange when filter chip is clicked`() {
        var selectedType: SearchType? = null
        val searchState = SearchState(
            query = "",
            type = SearchType.Name,
        )

        composeTestRule.setContent {
            AppTheme {
                DrinkSearchBarSection(
                    searchState = searchState,
                    onQueryChange = {},
                    onSearchTypeChange = { selectedType = it },
                    onSearch = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Ingredient").performClick()
        selectedType shouldBe SearchType.Ingredient
    }

    @Test
    fun `triggers onSearch when search icon is clicked`() {
        var searchSubmitted = false
        val searchState = SearchState(
            query = "Margarita",
            type = SearchType.Name,
        )

        composeTestRule.setContent {
            AppTheme {
                DrinkSearchBarSection(
                    searchState = searchState,
                    onQueryChange = {},
                    onSearchTypeChange = {},
                    onSearch = { searchSubmitted = true },
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Search").performClick()
        searchSubmitted shouldBe true
    }
}
