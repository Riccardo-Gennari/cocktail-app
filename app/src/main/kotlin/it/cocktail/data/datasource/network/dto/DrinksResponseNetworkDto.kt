package it.cocktail.data.datasource.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Network DTO wrapping a list of [DrinkSummaryNetworkDto].
 */
@Serializable
data class DrinksResponseNetworkDto(
    @Serializable(with = DrinkListSerializer::class)
    @SerialName("drinks")
    val drinks: List<DrinkSummaryNetworkDto>? = null,
)

/**
 * [SafeListSerializer] for the `drinks` field in [DrinksResponseNetworkDto].
 * @see SafeListSerializer
 */
object DrinkListSerializer : SafeListSerializer<DrinkSummaryNetworkDto>(DrinkSummaryNetworkDto.serializer())
