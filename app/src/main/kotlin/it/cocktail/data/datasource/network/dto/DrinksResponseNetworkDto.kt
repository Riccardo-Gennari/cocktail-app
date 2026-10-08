package it.cocktail.data.datasource.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DrinksResponseNetworkDto(
    @Serializable(with = DrinkListSerializer::class)
    @SerialName("drinks")
    val drinks: List<DrinkSummaryNetworkDto>? = null,
)

/**
 * Custom serializer for the `drinks` field in [DrinksResponseNetworkDto].
 */
object DrinkListSerializer : SafeListSerializer<DrinkSummaryNetworkDto>(DrinkSummaryNetworkDto.serializer())
