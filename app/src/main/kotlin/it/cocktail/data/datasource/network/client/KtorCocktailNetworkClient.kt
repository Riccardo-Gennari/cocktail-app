package it.cocktail.data.datasource.network.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import it.cocktail.core.common.util.runCatchingCancelable
import it.cocktail.data.datasource.network.dto.DrinksResponseDto
import kotlinx.serialization.json.Json
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class KtorCocktailNetworkClient(
    apiKey: String = PUBLIC_API_KEY,
    engine: HttpClientEngine = defaultEngine(),
    timeout: Duration = 30.seconds,
) : CocktailNetworkClient {
    private val baseUrl = "https://www.thecocktaildb.com/api/json/v1/$apiKey/"

    private val client =
        HttpClient(engine) {
            expectSuccess = true
            followRedirects = false
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    },
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = timeout.inWholeMilliseconds
                connectTimeoutMillis = timeout.inWholeMilliseconds
                socketTimeoutMillis = timeout.inWholeMilliseconds
            }
        }

    override suspend fun searchByName(name: String): Result<DrinksResponseDto> =
        runCatchingCancelable {
            client
                .get("$baseUrl/search.php") { parameter("s", name) }
                .body<DrinksResponseDto>()
        }

    override suspend fun searchByIngredient(ingredient: String): Result<DrinksResponseDto> =
        runCatchingCancelable {
            client
                .get("$baseUrl/filter.php") { parameter("i", ingredient) }
                .body<DrinksResponseDto>()
        }

    companion object {
        const val PUBLIC_API_KEY = "1"

        fun defaultEngine(): HttpClientEngine =
            OkHttpEngine(
                OkHttpConfig().apply {
                    config {
                        followRedirects(false)
                        followSslRedirects(false)
                    }
                },
            )
    }
}
