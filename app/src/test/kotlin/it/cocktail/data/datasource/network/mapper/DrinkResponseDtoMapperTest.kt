package it.cocktail.data.datasource.network.mapper

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import it.cocktail.data.datasource.network.dto.DrinkSummaryDto
import it.cocktail.data.datasource.network.dto.DrinksResponseDto
import it.cocktail.domain.model.DrinkSummary

class DrinkResponseDtoMapperTest :
    BehaviorSpec(
        {
            Given("a DrinksResponseDto") {
                When("drinks list is null") {
                    val dto = DrinksResponseDto(drinks = null)

                    Then("toDomain should return an empty list") {
                        dto.toDomain().shouldBeEmpty()
                    }
                }

                When("drinks list is empty") {
                    val dto = DrinksResponseDto(drinks = emptyList())

                    Then("toDomain should return an empty list") {
                        dto.toDomain().shouldBeEmpty()
                    }
                }

                When("drinks list contains valid drink DTOs") {
                    val dto = DrinksResponseDto(
                        drinks = listOf(
                            DrinkSummaryDto(id = 1, name = "name 1", imageUri = "image 1"),
                            DrinkSummaryDto(id = 2, name = "name 2", imageUri = "image 2"),
                        ),
                    )

                    Then("toDomain should map all DTOs to DrinkSummary domain models") {
                        dto.toDomain() shouldBe listOf(
                            DrinkSummary(id = 1, name = "name 1", imageUri = "image 1/small"),
                            DrinkSummary(id = 2, name = "name 2", imageUri = "image 2/small"),
                        )
                    }
                }

                When("drinks list contains invalid DTOs with null id or blank name") {
                    val dto = DrinksResponseDto(
                        drinks = listOf(
                            DrinkSummaryDto(id = 1, name = "name 1", imageUri = "image 1"),
                            DrinkSummaryDto(id = null, name = "name 2", imageUri = "image 2"),
                            DrinkSummaryDto(id = 2, name = "   ", imageUri = "image 3"),
                            DrinkSummaryDto(id = 3, name = null, imageUri = "image 4"),
                        ),
                    )

                    Then("toDomain should filter out invalid DTOs and keep valid ones") {
                        dto.toDomain() shouldBe listOf(
                            DrinkSummary(id = 1, name = "name 1", imageUri = "image 1/small"),
                        )
                    }
                }
            }
        },
    )
