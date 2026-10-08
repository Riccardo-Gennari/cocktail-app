package it.cocktail.data.datasource.network.mapper

import it.cocktail.data.datasource.network.dto.DrinksResponseDto
import it.cocktail.domain.model.DrinkSummary

fun DrinksResponseDto.toDomain(): List<DrinkSummary> = drinks.orEmpty().mapNotNull { it.toDomain() }
