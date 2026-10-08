package it.cocktail.data.repository

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import it.cocktail.data.datasource.network.client.CocktailNetworkClient
import it.cocktail.data.datasource.network.dto.DrinkDetailDto
import it.cocktail.data.datasource.network.dto.DrinkDetailResponseDto
import it.cocktail.data.datasource.network.mapper.toDomain

class DefaultDrinkDetailRepositoryTest :
    BehaviorSpec({
        val dtoId = 1
        val expectedDto = DrinkDetailResponseDto(
            drinks = listOf(
                DrinkDetailDto(
                    id = dtoId,
                    name = "name",
                    category = "category",
                    ingredient1 = "ingredient1",
                    measure1 = "measure1",
                ),
            ),
        )

        Given("an instance") {
            val networkClient = mockk<CocktailNetworkClient>()
            val repository = DefaultDrinkDetailRepository(networkClient)

            When("getDetail is triggered") {
                And("network call succeeds") {
                    coEvery { networkClient.getDrinkDetail(dtoId) } returns Result.success(expectedDto)

                    Then("it returns a successful Result with domain data") {
                        val result = repository.getDetail(dtoId)
                        result.isSuccess shouldBe true
                        result.getOrThrow() shouldBe expectedDto.drinks!!.first().toDomain()
                    }
                }
                And("network call fails") {
                    val exception = IllegalStateException("msg")
                    coEvery { networkClient.getDrinkDetail(dtoId) } returns Result.failure(exception)

                    Then("it returns a failed Result") {
                        val result = repository.getDetail(dtoId)
                        result.isFailure shouldBe true
                        result.exceptionOrNull() shouldBe exception
                    }
                }
            }
        }
    })
