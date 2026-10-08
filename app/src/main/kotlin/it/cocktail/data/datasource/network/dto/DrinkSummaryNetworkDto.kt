package it.cocktail.data.datasource.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DrinkSummaryNetworkDto(
    @SerialName("idDrink") val id: Int? = null,
    @SerialName("strDrink") val name: String? = null,
    @SerialName("strDrinkThumb") val imageUri: String? = null,
)
