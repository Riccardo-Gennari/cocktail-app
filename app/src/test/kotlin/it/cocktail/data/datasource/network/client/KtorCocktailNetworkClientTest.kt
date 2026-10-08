package it.cocktail.data.datasource.network.client

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.spec.style.scopes.ShouldSpecContainerScope
import io.kotest.matchers.shouldBe
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import it.cocktail.data.datasource.network.dto.DrinkDetailNetworkDto
import it.cocktail.data.datasource.network.dto.DrinkDetailResponseNetworkDto
import it.cocktail.data.datasource.network.dto.DrinkSummaryNetworkDto
import it.cocktail.data.datasource.network.dto.DrinksResponseNetworkDto
import kotlinx.serialization.json.Json

class KtorCocktailNetworkClientTest :
    ShouldSpec({
        val summaryDto = DrinksResponseNetworkDto(
            drinks = listOf(
                DrinkSummaryNetworkDto(
                    id = 11007,
                    name = "name",
                    imageUri = "imageUri",
                ),
            ),
        )

        val detailDto = DrinkDetailResponseNetworkDto(
            drinks = listOf(
                DrinkDetailNetworkDto(
                    id = 1,
                    name = "name",
                    category = "category",
                    ingredient1 = "ingredient1",
                    measure1 = "measure1",
                ),
            ),
        )

        suspend fun <T, U> ShouldSpecContainerScope.testEndpoint(
            call: suspend KtorCocktailNetworkClient.() -> Result<T>,
            validClient: KtorCocktailNetworkClient,
            validDto: T,
            extractDrinks: (T?) -> List<U>?,
        ) {
            should("return Result.success on valid 200 response") {
                val result = validClient.call()

                result.isSuccess shouldBe true
                result.getOrNull() shouldBe validDto
            }

            should("return Result.failure on 500 HTTP error") {
                ServerErrorClient.call().isFailure shouldBe true
            }

            should("return Result.failure on malformed JSON response") {
                InvalidJsonClient.call().isFailure shouldBe true
            }

            should("return Result.success with empty list when API returns 'no data found'") {
                val result = successClient("""{"drinks": "no data found"}""").call()

                result.isSuccess shouldBe true
                extractDrinks(result.getOrNull()) shouldBe emptyList()
            }

            should("return Result.success when API returns null drinks") {
                val result = successClient("""{"drinks": null}""").call()

                result.isSuccess shouldBe true
                extractDrinks(result.getOrNull()) shouldBe null
            }
        }

        context("searchByName") {
            testEndpoint(
                call = { searchByName("name") },
                validClient = successClientDto(summaryDto),
                validDto = summaryDto,
                extractDrinks = { it?.drinks },
            )
        }

        context("searchByIngredient") {
            testEndpoint(
                call = { searchByIngredient("name") },
                validClient = successClientDto(summaryDto),
                validDto = summaryDto,
                extractDrinks = { it?.drinks },
            )
        }

        context("getDrinkDetail") {
            testEndpoint(
                call = { getDrinkDetail(1) },
                validClient = successClientDto(detailDto),
                validDto = detailDto,
                extractDrinks = { it?.drinks },
            )
        }
    }) {
    private companion object MockClient {
        val InvalidJsonClient = KtorCocktailNetworkClient(
            apiKey = "any",
            engine = MockEngine { _ ->
                respond(
                    content = "{ invalid json }",
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json"),
                )
            },
        )

        val ServerErrorClient = KtorCocktailNetworkClient(
            apiKey = "any",
            engine = MockEngine { _ ->
                respond(
                    content = "Internal Server Error",
                    status = HttpStatusCode.InternalServerError,
                    headers = headersOf(HttpHeaders.ContentType, "text/plain"),
                )
            },
        )

        fun successClient(json: String) =
            KtorCocktailNetworkClient(
                apiKey = "any",
                engine = MockEngine { _ ->
                    respond(
                        content = json,
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                },
            )

        inline fun <reified T> successClientDto(dto: T) = successClient(Json.encodeToString(dto))
    }
}
