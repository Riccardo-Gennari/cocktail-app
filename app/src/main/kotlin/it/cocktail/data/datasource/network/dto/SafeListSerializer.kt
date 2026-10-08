package it.cocktail.data.datasource.network.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer

/**
 * Custom serializer for list fields in TheCocktailDB API responses.
 *
 * TheCocktailDB API returns `{"drinks": "no data found"}` or similar messages instead of an array or `null`
 * when a search yields no results.
 *
 * This serializer transforms any non-array JSON element into an empty [JsonArray] during
 * deserialization to prevent deserialization errors and safely parse empty results.
 */
open class SafeListSerializer<T>(
    elementSerializer: KSerializer<T>,
) : JsonTransformingSerializer<List<T>>(ListSerializer(elementSerializer)) {
    override fun transformDeserialize(element: JsonElement): JsonElement = (element as? JsonArray) ?: JsonArray(emptyList())
}
