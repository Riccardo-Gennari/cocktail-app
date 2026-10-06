package it.cocktail.data.repository

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import it.cocktail.data.datasource.network.client.CocktailNetworkClient
import it.cocktail.data.datasource.network.dto.DrinkSummaryDto
import it.cocktail.data.datasource.network.dto.DrinksResponseDto
import it.cocktail.domain.mapper.toDomain

class DefaultDrinkSummaryRepositoryTest :
    BehaviorSpec({
        Given("an instance") {
            val networkClient = mockk<CocktailNetworkClient>()
            val repository = DefaultDrinkSummaryRepository(networkClient)

            When("onSearch is called") {
                And("network call succeeds") {
                    val expectedSummaryDto = List(3) {
                        DrinkSummaryDto(id = it, name = "name $it", imageUri = "image $it")
                    }

                    coEvery { networkClient.searchByName("query") } returns Result.success(
                        DrinksResponseDto(expectedSummaryDto),
                    )

                    Then("it should return a successful Result with domain data") {
                        val result = repository.searchByName(query = "query")
                        result.isSuccess shouldBe true
                        result.getOrThrow() shouldBe expectedSummaryDto.map { it.toDomain() }
                    }
                }
                And("network call fails") {
                    val exception = IllegalStateException("msg")

                    coEvery { networkClient.searchByName("query") } returns Result.failure(exception)

                    Then("it should return a failed Result") {
                        val result = repository.searchByName(query = "query")
                        result.isFailure shouldBe true
                        result.exceptionOrNull() shouldBe exception
                    }
                }
            }
        }
    })
