package it.cocktail.data.datasource.network.mapper

import it.cocktail.data.datasource.network.dto.DrinksResponseNetworkDto
import it.cocktail.domain.model.DrinkSummary

/**
 * Maps [DrinksResponseNetworkDto] to a list of domain [DrinkSummary].
 */
fun DrinksResponseNetworkDto.toDomain(): List<DrinkSummary> = drinks.orEmpty().mapNotNull { it.toDomain() }
