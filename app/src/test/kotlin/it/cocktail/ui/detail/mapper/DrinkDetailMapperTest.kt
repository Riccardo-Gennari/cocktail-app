package it.cocktail.ui.detail.mapper

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import it.cocktail.core.ui.model.ImageUri
import it.cocktail.domain.model.DrinkDetail
import it.cocktail.domain.model.Ingredient
import it.cocktail.ui.detail.model.DrinkDetailUiModel

class DrinkDetailMapperTest :
    BehaviorSpec(
        {
            Given("a domain model with all fields populated") {
                val domainDrink = DrinkDetail(
                    id = 1,
                    name = "name 1",
                    category = "category 1",
                    alcoholic = " ", // this one is blank and should be excluded
                    glass = "glass 1",
                    instructions = "instructions 1",
                    imageUri = "image 1",
                    ingredients = listOf(
                        Ingredient(name = "ingredient 1", measure = "measure 1"),
                        Ingredient(name = "ingredient 2", measure = null),
                    ),
                )

                When("it is converted to a ui model") {
                    val uiDrink = domainDrink.toUi()

                    Then("all fields should be mapped correctly") {
                        uiDrink shouldBe DrinkDetailUiModel(
                            id = 1,
                            name = "name 1",
                            imageUri = ImageUri("image 1"),
                            ingredients = domainDrink.ingredients.map { it.toUi() },
                            instructions = "instructions 1",
                            tags = listOf("category 1", "glass 1"),
                        )
                    }
                }
            }

            Given("a domain model with all fields empty") {
                val domainDrink = DrinkDetail(id = 1)

                When("it is converted to a ui model") {
                    val uiDrink = domainDrink.toUi()

                    Then("all fields should be mapped correctly") {
                        uiDrink shouldBe DrinkDetailUiModel(
                            id = 1,
                            name = "",
                            imageUri = ImageUri(""),
                            ingredients = emptyList(),
                            instructions = "",
                            tags = emptyList(),
                        )
                    }
                }
            }
        },
    )
