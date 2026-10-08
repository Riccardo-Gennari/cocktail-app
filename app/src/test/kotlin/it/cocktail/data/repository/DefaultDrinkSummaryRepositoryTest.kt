package it.cocktail.data.repository

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import it.cocktail.data.datasource.local.PersistentStorage
import it.cocktail.data.datasource.local.dto.DrinkSummaryLocalDto
import it.cocktail.data.datasource.network.client.CocktailNetworkClient
import it.cocktail.data.datasource.network.dto.DrinkSummaryNetworkDto
import it.cocktail.data.datasource.network.dto.DrinksResponseNetworkDto
import it.cocktail.data.datasource.network.mapper.toDomain
import it.cocktail.domain.model.DrinkSummary

class DefaultDrinkSummaryRepositoryTest :
    BehaviorSpec({
        val drinksResponse = DrinksResponseNetworkDto(
            drinks = List(3) {
                DrinkSummaryNetworkDto(id = it, name = "name $it", imageUri = "image $it")
            },
        )
        val localDtoDrinks = listOf(DrinkSummaryLocalDto(id = 1, name = "name 1", imageUri = "image 1"))
        val domainDrinks = listOf(DrinkSummary(id = 1, name = "name 1", imageUri = "image 1"))

        Given("an instance") {
            val networkClient = mockk<CocktailNetworkClient>()
            val persistentStorage = mockk<PersistentStorage>()
            val repository = DefaultDrinkSummaryRepository(networkClient, persistentStorage)

            When("searchByIngredient is called") {
                And("network call succeeds") {
                    coEvery { networkClient.searchByIngredient("ingredient") } returns Result.success(drinksResponse)

                    Then("it returns a successful Result with domain data") {
                        val result = repository.searchByIngredient("ingredient")
                        result.isSuccess shouldBe true
                        result.getOrThrow() shouldBe drinksResponse.drinks?.map { it.toDomain() }
                    }
                }
                And("network call fails") {
                    val exception = IllegalStateException("msg")
                    coEvery { networkClient.searchByIngredient("ingredient") } returns Result.failure(exception)

                    Then("it returns a failed Result") {
                        val result = repository.searchByIngredient("ingredient")
                        result.isFailure shouldBe true
                        result.exceptionOrNull() shouldBe exception
                    }
                }
            }

            When("searchByName is called") {
                And("network call succeeds") {
                    coEvery { networkClient.searchByName("name") } returns Result.success(drinksResponse)

                    Then("it returns a successful Result with domain data") {
                        val result = repository.searchByName("name")
                        result.isSuccess shouldBe true
                        result.getOrThrow() shouldBe drinksResponse.drinks?.map { it.toDomain() }
                    }
                }
                And("network call fails") {
                    val exception = IllegalStateException("msg")
                    coEvery { networkClient.searchByName("name") } returns Result.failure(exception)

                    Then("it returns a failed Result") {
                        val result = repository.searchByName("name")
                        result.isFailure shouldBe true
                        result.exceptionOrNull() shouldBe exception
                    }
                }
            }

            When("getStoredDrinks is called") {
                coEvery { persistentStorage.getDrinkSummaries() } returns Result.success(localDtoDrinks)

                Then("it returns stored domain drinks successfully") {
                    val result = repository.getStoredDrinks()
                    result.isSuccess shouldBe true
                    result.getOrThrow() shouldBe domainDrinks
                }
            }

            When("storeDrinks is called") {
                coEvery { persistentStorage.storeDrinkSummaries(any()) } returns Result.success(Unit)

                Then("it stores drinks successfully") {
                    val result = repository.storeDrinks(domainDrinks)
                    result.isSuccess shouldBe true
                }
            }
        }
    })
