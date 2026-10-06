package it.cocktail.data.datasource.network.client

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import it.cocktail.data.datasource.network.dto.DrinkSummaryDto
import it.cocktail.data.datasource.network.dto.DrinksResponseDto
import kotlinx.serialization.json.Json

class KtorCocktailNetworkClientTest :
    ShouldSpec({
        withData<suspend KtorCocktailNetworkClient.(query: String) -> Result<DrinksResponseDto>>(
            mapOf(
                "searchByName" to { searchByName(it) },
                "searchByIngredient" to { searchByIngredient(it) },
            ),
        ) { query ->
            should("return Result.success on valid 200 response") {
                val expectedDto = DrinksResponseDto(
                    drinks = listOf(
                        DrinkSummaryDto(
                            id = 11007,
                            name = "name",
                            imageUri = "imageUri",
                        ),
                    ),
                )

                val client = KtorCocktailNetworkClient(engine = ok(expectedDto), apiKey = "")
                val result = client.query("name")

                result.isSuccess shouldBe true
                val dto = result.getOrNull()
                dto shouldBe expectedDto
            }

            should("return Result.failure on 500 HTTP error") {
                val client = KtorCocktailNetworkClient(engine = ServerError, apiKey = "")
                val result = client.query("name")

                result.isFailure shouldBe true
            }

            should("return Result.failure on malformed JSON response") {
                val client = KtorCocktailNetworkClient(engine = InvalidJson, apiKey = "")
                val result = client.query("name")

                result.isFailure shouldBe true
            }

            should("return Result.success with empty list when API returns 'no data found'") {
                val client = KtorCocktailNetworkClient(engine = ok("""{"drinks": "no data found"}"""), apiKey = "")
                val result = client.query("name")

                result.isSuccess shouldBe true
                result.getOrNull()?.drinks shouldBe emptyList()
            }

            should("return Result.success when API returns null drinks") {
                val client = KtorCocktailNetworkClient(engine = ok("""{"drinks": null}"""), apiKey = "")
                val result = client.query("name")

                result.isSuccess shouldBe true
                result.getOrNull()?.drinks shouldBe null
            }
        }
    }) {
    private companion object MockEngine {
        val InvalidJson = MockEngine { _ ->
            respond(
                content = "{ invalid json }",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val ServerError = MockEngine { _ ->
            respond(
                content = "Internal Server Error",
                status = HttpStatusCode.InternalServerError,
                headers = headersOf(HttpHeaders.ContentType, "text/plain"),
            )
        }

        fun ok(json: String) =
            MockEngine { _ ->
                respond(
                    content = json,
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json"),
                )
            }

        fun ok(dto: DrinksResponseDto) = ok(Json.encodeToString(dto))
    }
}
