package it.cocktail.data.datasource.network.mapper

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import it.cocktail.data.datasource.network.client.CocktailEndpoint
import it.cocktail.data.datasource.network.dto.DrinkDetailDto
import it.cocktail.domain.model.DrinkDetail
import it.cocktail.domain.model.Ingredient

class DrinkDetailDtoMapperTest :
    BehaviorSpec(
        {
            Given("a DrinkDetailDto") {
                When("id is null") {
                    val dto = DrinkDetailDto(id = null, name = "name 1")

                    Then("toDomain should return null") {
                        dto.toDomain().shouldBeNull()
                    }
                }

                When("name is null or blank") {
                    Then("toDomain should return null") {
                        DrinkDetailDto(id = 1, name = null).toDomain().shouldBeNull()
                        DrinkDetailDto(id = 1, name = "   ").toDomain().shouldBeNull()
                    }
                }

                When("all fields are valid") {
                    val dto = DrinkDetailDto(
                        id = 1,
                        name = "name 1",
                        category = "category 1",
                        alcoholic = "alcoholic 1",
                        glass = "glass 1",
                        instructions = "instructions 1",
                        imageUri = "image 1",
                        ingredient1 = " ingredient 1 ",
                        measure1 = " measure 1 ",
                        ingredient2 = "ingredient 2",
                        measure2 = null,
                        ingredient3 = "ingredient 3",
                        measure3 = "   ",
                        ingredient4 = "   ",
                        measure4 = "measure 4",
                    )

                    Then("toDomain should correctly map to DrinkDetail") {
                        dto.toDomain() shouldBe DrinkDetail(
                            id = 1,
                            name = "name 1",
                            category = "category 1",
                            alcoholic = "alcoholic 1",
                            glass = "glass 1",
                            instructions = "instructions 1",
                            imageUri = "image 1",
                            ingredients = listOf(
                                Ingredient(
                                    name = "ingredient 1",
                                    measure = "measure 1",
                                    imageUri = CocktailEndpoint.ingredientThumbnailPath("ingredient 1"),
                                ),
                                Ingredient(
                                    name = "ingredient 2",
                                    measure = null,
                                    imageUri = CocktailEndpoint.ingredientThumbnailPath("ingredient 2"),
                                ),
                                Ingredient(
                                    name = "ingredient 3",
                                    measure = null,
                                    imageUri = CocktailEndpoint.ingredientThumbnailPath("ingredient 3"),
                                ),
                            ),
                        )
                    }
                }

                When("optional fields are null") {
                    val dto = DrinkDetailDto(
                        id = 1,
                        name = "name 1",
                        category = null,
                        alcoholic = null,
                        glass = null,
                        instructions = null,
                        imageUri = null,
                    )

                    Then("toDomain should default null string fields to empty strings and empty ingredients") {
                        dto.toDomain() shouldBe DrinkDetail(
                            id = 1,
                            name = "name 1",
                            category = "",
                            alcoholic = "",
                            glass = "",
                            instructions = "",
                            imageUri = "",
                            ingredients = emptyList(),
                        )
                    }
                }
            }
        },
    )
