package it.cocktail.data.datasource.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DrinksResponseDto(
    @Serializable(with = DrinkListSerializer::class)
    @SerialName("drinks")
    val drinks: List<DrinkSummaryDto>? = null,
)

/**
 * Custom serializer for the `drinks` field in [DrinksResponseDto].
 */
object DrinkListSerializer : SafeListSerializer<DrinkSummaryDto>(DrinkSummaryDto.serializer())
