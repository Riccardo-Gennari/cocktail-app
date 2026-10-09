package it.cocktail.data.datasource.local.room

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import it.cocktail.core.common.log.Logger
import it.cocktail.data.datasource.local.dto.DrinkDetailLocalDto
import it.cocktail.data.datasource.local.dto.DrinkSummaryLocalDto
import it.cocktail.data.datasource.local.dto.IngredientLocalDto

class RoomPersistentStorageTest :
    BehaviorSpec(
        {
            val drinksDao = mockk<DrinksDao>()
            val log = mockk<Logger>(relaxed = true)
            val storage = RoomPersistentStorage(drinksDao, log)

            val sampleSummary = DrinkSummaryLocalDto(id = 1, name = "name", imageUri = "imageUri")
            val sampleDetail = DrinkDetailLocalDto(
                id = 1,
                name = "name",
                category = "category",
                alcoholic = "alcoholic",
                glass = "glass",
                instructions = "instructions",
                imageUri = "imageUri",
                ingredients = listOf(IngredientLocalDto("ingredient", "measure", "imageUri")),
            )

            Given("storeDrinkSummaries") {
                When("dao succeeds") {
                    coEvery { drinksDao.replaceDrinkSummaries(listOf(sampleSummary)) } returns Unit

                    Then("returns success result and delegates to dao") {
                        val result = storage.storeDrinkSummaries(listOf(sampleSummary))
                        result.isSuccess shouldBe true
                        coVerify(exactly = 1) { drinksDao.replaceDrinkSummaries(listOf(sampleSummary)) }
                    }
                }

                When("dao throws exception") {
                    val exception = RuntimeException("Database error")
                    coEvery { drinksDao.replaceDrinkSummaries(any()) } throws exception

                    Then("returns failure result") {
                        val result = storage.storeDrinkSummaries(listOf(sampleSummary))
                        result.isFailure shouldBe true
                        result.exceptionOrNull() shouldBe exception
                    }
                }
            }

            Given("getDrinkSummaries") {
                When("dao succeeds") {
                    coEvery { drinksDao.getDrinkSummaries() } returns listOf(sampleSummary)

                    Then("returns drink summaries successfully") {
                        val result = storage.getDrinkSummaries()
                        result.isSuccess shouldBe true
                        result.getOrNull() shouldBe listOf(sampleSummary)
                    }
                }

                When("dao throws exception") {
                    val exception = RuntimeException("Database error")
                    coEvery { drinksDao.getDrinkSummaries() } throws exception

                    Then("returns failure result") {
                        val result = storage.getDrinkSummaries()
                        result.isFailure shouldBe true
                        result.exceptionOrNull() shouldBe exception
                    }
                }
            }

            Given("storeDrinkDetail") {
                When("dao succeeds") {
                    coEvery { drinksDao.insertDrinkDetail(sampleDetail) } returns Unit

                    Then("returns success result and delegates to dao") {
                        val result = storage.storeDrinkDetail(sampleDetail)
                        result.isSuccess shouldBe true
                        coVerify(exactly = 1) { drinksDao.insertDrinkDetail(sampleDetail) }
                    }
                }

                When("dao throws exception") {
                    val exception = RuntimeException("Database error")
                    coEvery { drinksDao.insertDrinkDetail(any()) } throws exception

                    Then("returns failure result") {
                        val result = storage.storeDrinkDetail(sampleDetail)
                        result.isFailure shouldBe true
                        result.exceptionOrNull() shouldBe exception
                    }
                }
            }

            Given("getDrinkDetail") {
                When("dao succeeds") {
                    coEvery { drinksDao.getDrinkDetail(1) } returns sampleDetail

                    Then("returns drink detail successfully") {
                        val result = storage.getDrinkDetail(1)
                        result.isSuccess shouldBe true
                        result.getOrNull() shouldBe sampleDetail
                    }
                }

                When("dao throws exception") {
                    val exception = RuntimeException("Database error")
                    coEvery { drinksDao.getDrinkDetail(1) } throws exception

                    Then("returns failure result") {
                        val result = storage.getDrinkDetail(1)
                        result.isFailure shouldBe true
                        result.exceptionOrNull() shouldBe exception
                    }
                }
            }
        },
    )
