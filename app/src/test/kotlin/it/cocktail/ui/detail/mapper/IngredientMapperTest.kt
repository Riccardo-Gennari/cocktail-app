package it.cocktail.ui.detail.mapper

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import it.cocktail.core.ui.model.ImageUri
import it.cocktail.domain.model.Ingredient
import it.cocktail.ui.detail.model.IngredientUiModel

class IngredientMapperTest :
    BehaviorSpec(
        {
            Given("a domain model") {
                When("toUi is called") {
                    val ingredient = Ingredient(name = "ingredient 1", measure = "measure 1", imageUri = "image 1")

                    Then("it should map all fields correctly") {
                        ingredient.toUi() shouldBe IngredientUiModel(
                            name = "ingredient 1",
                            measure = "measure 1",
                            image = ImageUri("image 1"),
                        )
                    }
                }
            }
        },
    )
