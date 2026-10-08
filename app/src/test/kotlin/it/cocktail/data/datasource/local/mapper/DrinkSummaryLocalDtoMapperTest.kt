package it.cocktail.data.datasource.local.mapper

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import it.cocktail.data.datasource.local.dto.DrinkSummaryLocalDto
import it.cocktail.domain.model.DrinkSummary

class DrinkSummaryLocalDtoMapperTest :
    ShouldSpec({
        should("map dto to domain correctly") {
            val dto = DrinkSummaryLocalDto(
                id = 1,
                name = "name",
                imageUri = "imageUri",
            )

            dto.toDomain() shouldBe DrinkSummary(
                id = 1,
                name = "name",
                imageUri = "imageUri",
            )
        }

        should("map domain to dto correctly") {
            val domain = DrinkSummary(
                id = 1,
                name = "name",
                imageUri = "imageUri",
            )

            domain.toLocalDto() shouldBe DrinkSummaryLocalDto(
                id = 1,
                name = "name",
                imageUri = "imageUri",
            )
        }
    })
