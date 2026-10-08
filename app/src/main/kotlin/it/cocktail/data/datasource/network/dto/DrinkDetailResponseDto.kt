package it.cocktail.data.datasource.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DrinkDetailResponseDto(
    @Serializable(with = DrinkDetailListSerializer::class)
    @SerialName("drinks")
    val drinks: List<DrinkDetailDto>? = null,
)

/**
 * Custom serializer for the `drinks` field in [DrinkDetailResponseDto].
 */
object DrinkDetailListSerializer : SafeListSerializer<DrinkDetailDto>(DrinkDetailDto.serializer())
