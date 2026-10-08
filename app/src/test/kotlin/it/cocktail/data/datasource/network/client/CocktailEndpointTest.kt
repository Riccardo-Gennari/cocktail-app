package it.cocktail.data.datasource.network.client

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class CocktailEndpointTest :
    BehaviorSpec({
        Given("the object") {
            When("constructing apiPath") {
                Then("should return the correct formatted API URL without trailing slash") {
                    CocktailEndpoint.apiPath("12a3") shouldBe "https://www.thecocktaildb.com/api/json/v1/12a3"
                }
            }

            When("constructing ingredientThumbnailPath") {
                Then("should return the correct small ingredient image URL") {
                    CocktailEndpoint.ingredientThumbnailPath("Gin") shouldBe
                        "https://www.thecocktaildb.com/images/ingredients/Gin-small.png"
                }
            }

            When("constructing drinkSummaryThumbnailPath") {
                Then("should append /small to the full image path") {
                    CocktailEndpoint.drinkSummaryThumbnailPath(
                        "https://www.thecocktaildb.com/images/media/drink/12345.jpg",
                    ) shouldBe
                        "https://www.thecocktaildb.com/images/media/drink/12345.jpg/small"
                }
            }
        }
    })
