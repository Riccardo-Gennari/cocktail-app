package it.cocktail.data.datasource.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DrinkDetailResponseNetworkDto(
    @Serializable(with = DrinkDetailListSerializer::class)
    @SerialName("drinks")
    val drinks: List<DrinkDetailNetworkDto>? = null,
)

/**
 * Custom serializer for the `drinks` field in [DrinkDetailResponseNetworkDto].
 */
object DrinkDetailListSerializer : SafeListSerializer<DrinkDetailNetworkDto>(DrinkDetailNetworkDto.serializer())
