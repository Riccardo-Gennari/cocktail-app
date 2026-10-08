package it.cocktail.data.repository

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.scopes.BehaviorSpecContextContainerScope
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import it.cocktail.data.datasource.network.client.CocktailNetworkClient
import it.cocktail.data.datasource.network.dto.DrinkSummaryDto
import it.cocktail.data.datasource.network.dto.DrinksResponseDto
import it.cocktail.data.datasource.network.mapper.toDomain
import it.cocktail.domain.model.DrinkSummary

class DefaultDrinkSummaryRepositoryTest :
    BehaviorSpec({
        val summaryDto = DrinksResponseDto(
            drinks = List(3) {
                DrinkSummaryDto(id = it, name = "name $it", imageUri = "image $it")
            },
        )

        suspend fun BehaviorSpecContextContainerScope.testRepoCall(
            networkCall: suspend CocktailNetworkClient.() -> Result<DrinksResponseDto>,
            repoCall: suspend DefaultDrinkSummaryRepository.() -> Result<List<DrinkSummary>>,
            expectedDto: DrinksResponseDto,
            mapToDomain: (DrinksResponseDto) -> List<DrinkSummary?>?,
        ) {
            Given("an instance") {
                val networkClient = mockk<CocktailNetworkClient>()
                val repository = DefaultDrinkSummaryRepository(networkClient)

                When("repo call is triggered") {
                    And("network call succeeds") {
                        coEvery { networkClient.networkCall() } returns Result.success(expectedDto)

                        Then("it returns a successful Result with domain data") {
                            val result = repository.repoCall()
                            result.isSuccess shouldBe true
                            result.getOrThrow() shouldBe mapToDomain(expectedDto)
                        }
                    }
                    And("network call fails") {
                        val exception = IllegalStateException("msg")
                        coEvery { networkClient.networkCall() } returns Result.failure(exception)

                        Then("it returns a failed Result") {
                            val result = repository.repoCall()
                            result.isFailure shouldBe true
                            result.exceptionOrNull() shouldBe exception
                        }
                    }
                }
            }
        }

        context("searchByIngredient") {
            testRepoCall(
                networkCall = { searchByIngredient("ingredient") },
                repoCall = { searchByIngredient(ingredient = "ingredient") },
                expectedDto = summaryDto,
                mapToDomain = { dto -> dto.drinks?.map { it.toDomain() } },
            )
        }

        context("searchByName") {
            testRepoCall(
                networkCall = { searchByName("name") },
                repoCall = { searchByName(query = "name") },
                expectedDto = summaryDto,
                mapToDomain = { dto -> dto.drinks?.map { it.toDomain() } },
            )
        }
    })
