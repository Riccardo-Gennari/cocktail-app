package it.cocktail.data.datasource.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Network DTO wrapping a list of [DrinkDetailNetworkDto].
 */
@Serializable
data class DrinkDetailResponseNetworkDto(
    @Serializable(with = DrinkDetailListSerializer::class)
    @SerialName("drinks")
    val drinks: List<DrinkDetailNetworkDto>? = null,
)

/**
 * [SafeListSerializer] for the `drinks` field in [DrinkDetailResponseNetworkDto].
 * @see SafeListSerializer
 */
object DrinkDetailListSerializer : SafeListSerializer<DrinkDetailNetworkDto>(DrinkDetailNetworkDto.serializer())
