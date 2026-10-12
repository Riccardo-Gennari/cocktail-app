package it.cocktail.data.datasource.network.dto

import it.cocktail.domain.model.DrinkSummary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Network DTO of domain entity [DrinkSummary].
 */
@Serializable
data class DrinkSummaryNetworkDto(
    @SerialName("idDrink") val id: Int? = null,
    @SerialName("strDrink") val name: String? = null,
    @SerialName("strDrinkThumb") val imageUri: String? = null,
)
