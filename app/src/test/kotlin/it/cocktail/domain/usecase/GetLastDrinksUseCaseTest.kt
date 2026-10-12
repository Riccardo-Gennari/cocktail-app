package it.cocktail.domain.usecase

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import it.cocktail.domain.model.DrinkSummary
import it.cocktail.domain.repository.DrinkSummaryRepository

class GetLastDrinksUseCaseTest :
    BehaviorSpec({
        Given("an instance") {
            val repository = mockk<DrinkSummaryRepository>()
            val useCase = GetLastDrinksUseCase(repository)

            When("it is invoked") {
                And("repository returns success with drinks") {
                    val expectedDrinks = List(2) { index ->
                        DrinkSummary(id = index, name = "name-$index", imageUri = "image-$index")
                    }
                    coEvery { repository.getStoredDrinks() } returns Result.success(expectedDrinks)

                    Then("it should return success result with the drinks") {
                        val result = useCase()

                        result.isSuccess shouldBe true
                        result.getOrThrow() shouldBe expectedDrinks
                        coVerify(exactly = 1) { repository.getStoredDrinks() }
                    }
                }

                And("repository returns success with empty list") {
                    val expectedDrinks = emptyList<DrinkSummary>()
                    coEvery { repository.getStoredDrinks() } returns Result.success(expectedDrinks)

                    Then("it should return success result with empty list") {
                        val result = useCase()

                        result.isSuccess shouldBe true
                        result.getOrThrow() shouldBe expectedDrinks
                        coVerify(exactly = 1) { repository.getStoredDrinks() }
                    }
                }

                And("repository returns failure") {
                    val exception = IllegalStateException("Storage error")
                    coEvery { repository.getStoredDrinks() } returns Result.failure(exception)

                    Then("it should return failure result with exception") {
                        val result = useCase()

                        result.isFailure shouldBe true
                        result.exceptionOrNull() shouldBe exception
                        coVerify(exactly = 1) { repository.getStoredDrinks() }
                    }
                }
            }
        }
    })
