package it.cocktail.data.datasource.local.mapper

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import it.cocktail.data.datasource.local.dto.IngredientLocalDto
import it.cocktail.domain.model.Ingredient

class IngredientLocalDtoMapperTest :
    ShouldSpec({
        should("map dto to domain correctly") {
            val dto = IngredientLocalDto(
                name = "name",
                measure = "measure",
                imageUri = "imageUri",
            )

            dto.toDomain() shouldBe Ingredient(
                name = "name",
                measure = "measure",
                imageUri = "imageUri",
            )
        }

        should("map domain to dto correctly") {
            val domain = Ingredient(
                name = "name",
                measure = "measure",
                imageUri = "imageUri",
            )

            domain.toLocalDto() shouldBe IngredientLocalDto(
                name = "name",
                measure = "measure",
                imageUri = "imageUri",
            )
        }
    })
