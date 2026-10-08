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
import it.cocktail.data.datasource.network.dto.DrinkDetailResponseNetworkDto
import it.cocktail.data.datasource.network.dto.DrinksResponseNetworkDto
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Named
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class KtorCocktailNetworkClient(
    apiKey: String,
    engine: HttpClientEngine = defaultEngine(),
    timeout: Duration = defaultTimeout,
) : CocktailNetworkClient {
    @Inject
    constructor(
        @Named("cocktailApiKey") apiKey: String,
    ) : this(
        apiKey,
        engine = defaultEngine(),
        timeout = defaultTimeout,
    )

    private val baseUrl = CocktailEndpoint.apiPath(apiKey)

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

    override suspend fun getDrinkDetail(id: Int): Result<DrinkDetailResponseNetworkDto?> =
        runCatchingCancelable {
            client
                .get("$baseUrl/lookup.php") { parameter("i", id) }
                .body<DrinkDetailResponseNetworkDto>()
        }

    override suspend fun searchByIngredient(ingredient: String): Result<DrinksResponseNetworkDto> =
        runCatchingCancelable {
            client
                .get("$baseUrl/filter.php") { parameter("i", ingredient) }
                .body<DrinksResponseNetworkDto>()
        }

    override suspend fun searchByName(name: String): Result<DrinksResponseNetworkDto> =
        runCatchingCancelable {
            client
                .get("$baseUrl/search.php") { parameter("s", name) }
                .body<DrinksResponseNetworkDto>()
        }

    companion object {
        val defaultTimeout = 30.seconds

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
