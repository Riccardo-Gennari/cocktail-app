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
import it.cocktail.core.common.log.Logger
import it.cocktail.core.common.log.logTag
import it.cocktail.core.common.util.runCatchingCancelable
import it.cocktail.data.datasource.network.dto.DrinkDetailResponseNetworkDto
import it.cocktail.data.datasource.network.dto.DrinksResponseNetworkDto
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Named
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Implementation of [CocktailNetworkClient] that uses [HttpClient] for network communication.
 *
 * @param apiKey the CocktailDB API key use to authorize calls.
 * @param engine the [HttpClientEngine] used for network communication.
 * @param log the [Logger] used to log events.
 * @param timeout the [Duration] after which a request with no response is considered failed.
 */
class KtorCocktailNetworkClient(
    apiKey: String,
    engine: HttpClientEngine = defaultEngine(),
    private val log: Logger,
    timeout: Duration = defaultTimeout,
) : CocktailNetworkClient {
    @Inject
    constructor(
        @Named("cocktailApiKey") apiKey: String,
        log: Logger,
    ) : this(
        apiKey,
        engine = defaultEngine(),
        log = log,
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
            log.debug(TAG, "fetching drink detail for id: $id")
            client
                .get("$baseUrl/lookup.php") { parameter("i", id) }
                .body<DrinkDetailResponseNetworkDto>()
        }

    override suspend fun searchByIngredient(ingredient: String): Result<DrinksResponseNetworkDto> =
        runCatchingCancelable {
            log.debug(TAG, "fetching drinks for ingredient: $ingredient")
            client
                .get("$baseUrl/filter.php") { parameter("i", ingredient) }
                .body<DrinksResponseNetworkDto>()
        }

    override suspend fun searchByName(name: String): Result<DrinksResponseNetworkDto> =
        runCatchingCancelable {
            log.debug(TAG, "fetching drinks for name: $name")
            client
                .get("$baseUrl/search.php") { parameter("s", name) }
                .body<DrinksResponseNetworkDto>()
        }

    companion object {
        val defaultTimeout = 30.seconds

        val TAG = KtorCocktailNetworkClient::class.logTag()

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
