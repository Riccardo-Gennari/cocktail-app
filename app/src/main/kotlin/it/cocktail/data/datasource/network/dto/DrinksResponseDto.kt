package it.cocktail.data.datasource.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer

@Serializable
data class DrinksResponseDto(
    @Serializable(with = DrinkListSerializer::class)
    @SerialName("drinks")
    val drinks: List<DrinkSummaryDto>? = null,
)

/**
 * Custom serializer for the `drinks` field in [DrinksResponseDto].
 *
 * TheCocktailDB API returns `{"drinks": "no data found"}` or similiar messages instead of an array or `null`
 * when a search (e.g. by ingredient) yields no results.
 *
 * This serializer transforms any non-array JSON element into an empty [JsonArray] during
 * deserialization to prevent deserialization errors and safely parse empty results.
 */
object DrinkListSerializer :
    JsonTransformingSerializer<List<DrinkSummaryDto>>(ListSerializer(DrinkSummaryDto.serializer())) {
    override fun transformDeserialize(element: JsonElement): JsonElement = (element as? JsonArray) ?: JsonArray(emptyList())
}
