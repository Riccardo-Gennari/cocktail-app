package it.cocktail.domain.usecase

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import it.cocktail.domain.model.DrinkQuery
import it.cocktail.domain.model.DrinkSummary
import it.cocktail.domain.repository.DrinkSummaryRepository

class SearchDrinksUseCaseTest :
    BehaviorSpec({

        Given("an instance") {
            val repository = mockk<DrinkSummaryRepository> {
                coEvery { storeDrinks(any()) } returns Result.success(Unit)
            }
            val useCase = SearchDrinksUseCase(repository)

            And("DrinkQuery.ByName") {
                When("query is called with an empty string") {
                    Then("it should return empty list without calling repository") {
                        val result = useCase(DrinkQuery.ByName(""))

                        result.isSuccess shouldBe true
                        result.getOrThrow() shouldBe emptyList()
                        coVerify(exactly = 0) { repository.searchByName(any()) }
                    }
                }

                When("query is called with only whitespace") {
                    Then("it should return empty list without calling repository") {
                        val result = useCase(DrinkQuery.ByName("   "))

                        result.isSuccess shouldBe true
                        result.getOrThrow() shouldBe emptyList()
                        coVerify(exactly = 0) { repository.searchByName(any()) }
                    }
                }

                When("query is called with non-empty string requiring trimming") {
                    And("repository returns success") {
                        val expected = listOf(
                            DrinkSummary(id = 1, name = "name 1", imageUri = "image 1"),
                        )
                        coEvery { repository.searchByName("input") } returns Result.success(expected)

                        Then("it should call repository with trimmed query and return success result") {
                            val result = useCase(DrinkQuery.ByName("  input  "))

                            result.isSuccess shouldBe true
                            result.getOrThrow() shouldBe expected
                            coVerify(exactly = 1) { repository.searchByName("input") }
                        }
                    }

                    And("repository returns failure") {
                        val exception = IllegalStateException("Network error")
                        coEvery { repository.searchByName("input") } returns Result.failure(exception)

                        Then("it should call repository with trimmed query and return failure result") {
                            val result = useCase(DrinkQuery.ByName("  input  "))

                            result.isFailure shouldBe true
                            result.exceptionOrNull() shouldBe exception
                            coVerify(exactly = 1) { repository.searchByName("input") }
                        }
                    }
                }
            }

            And("DrinkQuery.ByIngredient") {
                When("query is called with an empty string") {
                    Then("it should return empty list without calling repository") {
                        val result = useCase(DrinkQuery.ByIngredient(""))

                        result.isSuccess shouldBe true
                        result.getOrThrow() shouldBe emptyList()
                        coVerify(exactly = 0) { repository.searchByIngredient(any()) }
                    }
                }

                When("query is called with only whitespace") {
                    Then("it should return empty list without calling repository") {
                        val result = useCase(DrinkQuery.ByIngredient("   "))

                        result.isSuccess shouldBe true
                        result.getOrThrow() shouldBe emptyList()
                        coVerify(exactly = 0) { repository.searchByIngredient(any()) }
                    }
                }

                When("query is called with non-empty string requiring trimming") {
                    And("repository returns success") {
                        val expected = listOf(
                            DrinkSummary(id = 1, name = "name 1", imageUri = "image 1"),
                        )
                        coEvery { repository.searchByIngredient("input") } returns Result.success(expected)

                        Then("it should call repository with trimmed query and return success result") {
                            val result = useCase(DrinkQuery.ByIngredient("  input  "))

                            result.isSuccess shouldBe true
                            result.getOrThrow() shouldBe expected
                            coVerify(exactly = 1) { repository.searchByIngredient("input") }
                        }
                    }

                    And("repository returns failure") {
                        val exception = IllegalStateException("Network error")
                        coEvery { repository.searchByIngredient("input") } returns Result.failure(exception)

                        Then("it should call repository with trimmed query and return failure result") {
                            val result = useCase(DrinkQuery.ByIngredient("  input  "))

                            result.isFailure shouldBe true
                            result.exceptionOrNull() shouldBe exception
                            coVerify(exactly = 1) { repository.searchByIngredient("input") }
                        }
                    }
                }
            }
        }
    })
