package it.cocktail.domain.usecase

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import it.cocktail.domain.model.DrinkDetail
import it.cocktail.domain.repository.DrinkDetailRepository

class GetDrinkDetailUseCaseTest :
    BehaviorSpec({
        Given("an instance") {
            val repository = mockk<DrinkDetailRepository>()
            val useCase = GetDrinkDetailUseCase(repository)

            When("it is invoked with a drink id") {
                val drinkId = 1
                val expectedDrink = DrinkDetail(id = drinkId, name = "name 1")

                And("repository returns success") {
                    coEvery { repository.getDetail(drinkId) } returns Result.success(expectedDrink)

                    Then("it should return success result with drink detail") {
                        val result = useCase(drinkId)

                        result.isSuccess shouldBe true
                        result.getOrThrow() shouldBe expectedDrink
                        coVerify(exactly = 1) { repository.getDetail(drinkId) }
                    }
                }

                And("repository returns failure") {
                    val exception = IllegalStateException("Network error")
                    coEvery { repository.getDetail(drinkId) } returns Result.failure(exception)

                    Then("it should return failure result") {
                        val result = useCase(drinkId)

                        result.isFailure shouldBe true
                        result.exceptionOrNull() shouldBe exception
                        coVerify(exactly = 1) { repository.getDetail(drinkId) }
                    }
                }
            }
        }
    })
