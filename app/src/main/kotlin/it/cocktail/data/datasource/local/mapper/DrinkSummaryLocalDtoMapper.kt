package it.cocktail.data.datasource.local.mapper

import it.cocktail.data.datasource.local.dto.DrinkSummaryLocalDto
import it.cocktail.domain.model.DrinkSummary

/**
 * Maps [DrinkSummaryLocalDto] to a domain [DrinkSummary].
 */
fun DrinkSummaryLocalDto.toDomain(): DrinkSummary =
    DrinkSummary(
        id = id,
        name = name,
        imageUri = imageUri,
    )

/**
 * Maps [DrinkSummary] to [DrinkSummaryLocalDto] for local storage.
 */
fun DrinkSummary.toLocalDto(): DrinkSummaryLocalDto =
    DrinkSummaryLocalDto(
        id = id,
        name = name,
        imageUri = imageUri,
    )
