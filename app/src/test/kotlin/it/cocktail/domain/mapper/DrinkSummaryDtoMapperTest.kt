package it.cocktail.domain.mapper

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import it.cocktail.data.datasource.network.dto.DrinkSummaryDto
import it.cocktail.domain.model.DrinkSummary

class DrinkSummaryDtoMapperTest :
    BehaviorSpec(
        {
            Given("a DrinkSummaryDto") {
                When("all fields are valid") {
                    val dto = DrinkSummaryDto(id = 1, name = "name 1", imageUri = "image 1")

                    Then("toDomain should return a DrinkSummary model") {
                        dto.toDomain() shouldBe DrinkSummary(
                            id = 1,
                            name = "name 1",
                            imageUri = "image 1",
                        )
                    }
                }

                When("imageUri is null") {
                    val dto = DrinkSummaryDto(id = 1, name = "name 1", imageUri = null)

                    Then("toDomain should return a DrinkSummary model with empty imageUri") {
                        dto.toDomain() shouldBe DrinkSummary(
                            id = 1,
                            name = "name 1",
                            imageUri = "",
                        )
                    }
                }

                When("id is null") {
                    val dto = DrinkSummaryDto(id = null, name = "name 1", imageUri = "image 1")

                    Then("toDomain should return null") {
                        dto.toDomain().shouldBeNull()
                    }
                }

                When("name is null") {
                    val dto = DrinkSummaryDto(id = 1, name = null, imageUri = "image 1")

                    Then("toDomain should return null") {
                        dto.toDomain().shouldBeNull()
                    }
                }

                When("name is blank") {
                    val dto = DrinkSummaryDto(id = 1, name = "   ", imageUri = "image 1")

                    Then("toDomain should return null") {
                        dto.toDomain().shouldBeNull()
                    }
                }
            }
        },
    )
