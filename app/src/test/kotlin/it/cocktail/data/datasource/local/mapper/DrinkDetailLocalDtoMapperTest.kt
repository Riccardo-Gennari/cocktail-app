package it.cocktail.data.datasource.local.mapper

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import it.cocktail.data.datasource.local.dto.DrinkDetailLocalDto
import it.cocktail.data.datasource.local.dto.IngredientLocalDto
import it.cocktail.domain.model.DrinkDetail
import it.cocktail.domain.model.Ingredient

class DrinkDetailLocalDtoMapperTest :
    ShouldSpec({
        should("map dto to domain correctly") {
            val dto = DrinkDetailLocalDto(
                id = 1,
                name = "name",
                category = "category",
                alcoholic = "alcoholic",
                glass = "glass",
                instructions = "instructions",
                imageUri = "imageUri",
                ingredients = listOf(
                    IngredientLocalDto(name = "name", measure = "measure", imageUri = "imageUri"),
                ),
            )

            dto.toDomain() shouldBe DrinkDetail(
                id = 1,
                name = "name",
                category = "category",
                alcoholic = "alcoholic",
                glass = "glass",
                instructions = "instructions",
                imageUri = "imageUri",
                ingredients = listOf(
                    Ingredient(name = "name", measure = "measure", imageUri = "imageUri"),
                ),
            )
        }

        should("map domain model to dto correctly") {
            val domain = DrinkDetail(
                id = 1,
                name = "name",
                category = "category",
                alcoholic = "alcoholic",
                glass = "glass",
                instructions = "instructions",
                imageUri = "imageUri",
                ingredients = listOf(
                    Ingredient(name = "name", measure = "measure", imageUri = "imageUri"),
                ),
            )

            domain.toLocalDto() shouldBe DrinkDetailLocalDto(
                id = 1,
                name = "name",
                category = "category",
                alcoholic = "alcoholic",
                glass = "glass",
                instructions = "instructions",
                imageUri = "imageUri",
                ingredients = listOf(
                    IngredientLocalDto(name = "name", measure = "measure", imageUri = "imageUri"),
                ),
            )
        }
    })
