package it.cocktail.data.repository

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import it.cocktail.data.datasource.local.PersistentStorage
import it.cocktail.data.datasource.local.dto.DrinkDetailLocalDto
import it.cocktail.data.datasource.local.mapper.toDomain
import it.cocktail.data.datasource.network.client.CocktailNetworkClient
import it.cocktail.data.datasource.network.dto.DrinkDetailNetworkDto
import it.cocktail.data.datasource.network.dto.DrinkDetailResponseNetworkDto
import it.cocktail.data.datasource.network.mapper.toDomain

class DefaultDrinkDetailRepositoryTest :
    BehaviorSpec({
        val dtoId = 1
        val expectedNetworkDto = DrinkDetailResponseNetworkDto(
            drinks = listOf(
                DrinkDetailNetworkDto(
                    id = dtoId,
                    name = "name",
                    category = "category",
                    ingredient1 = "ingredient1",
                    measure1 = "measure1",
                ),
            ),
        )
        val expectedDomainDrink = expectedNetworkDto.drinks!!.first().toDomain()
        val localDto = DrinkDetailLocalDto(
            id = dtoId,
            name = "stored name",
            category = "category",
            alcoholic = "alcoholic",
            glass = "glass",
            instructions = "instructions",
            imageUri = "imageUri",
            ingredients = emptyList(),
        )

        Given("an instance") {
            val networkClient = mockk<CocktailNetworkClient>()
            val persistentStorage = mockk<PersistentStorage>()
            val repository = DefaultDrinkDetailRepository(networkClient, persistentStorage)

            When("getDetail is triggered") {
                And("cache hit occurs") {
                    coEvery { persistentStorage.getDrinkDetail(dtoId) } returns Result.success(localDto)

                    Then("it returns a successful Result with stored domain data and does not call network") {
                        val result = repository.getDetail(dtoId)
                        result.isSuccess shouldBe true
                        result.getOrThrow() shouldBe localDto.toDomain()
                        coVerify(exactly = 0) { networkClient.getDrinkDetail(any()) }
                    }
                }
                And("cache miss occurs and network succeeds") {
                    coEvery { persistentStorage.getDrinkDetail(dtoId) } returns Result.success(null)
                    coEvery { networkClient.getDrinkDetail(dtoId) } returns Result.success(expectedNetworkDto)
                    coEvery { persistentStorage.storeDrinkDetail(any()) } returns Result.success(Unit)

                    Then("it returns network domain data and caches it") {
                        val result = repository.getDetail(dtoId)
                        result.isSuccess shouldBe true
                        result.getOrThrow() shouldBe expectedDomainDrink
                    }
                }
                And("cache read fails and network succeeds") {
                    coEvery { persistentStorage.getDrinkDetail(dtoId) } returns Result.failure(RuntimeException("db error"))
                    coEvery { networkClient.getDrinkDetail(dtoId) } returns Result.success(expectedNetworkDto)
                    coEvery { persistentStorage.storeDrinkDetail(any()) } returns Result.success(Unit)

                    Then("it falls back to network and caches result") {
                        val result = repository.getDetail(dtoId)
                        result.isSuccess shouldBe true
                        result.getOrThrow() shouldBe expectedDomainDrink
                    }
                }
                And("cache miss occurs and network call fails") {
                    val exception = IllegalStateException("msg")
                    coEvery { persistentStorage.getDrinkDetail(dtoId) } returns Result.success(null)
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
