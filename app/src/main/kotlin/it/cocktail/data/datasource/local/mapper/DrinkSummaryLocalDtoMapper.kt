package it.cocktail.data.datasource.local.mapper

import it.cocktail.data.datasource.local.dto.DrinkSummaryLocalDto
import it.cocktail.domain.model.DrinkSummary

fun DrinkSummaryLocalDto.toDomain(): DrinkSummary =
    DrinkSummary(
        id = id,
        name = name,
        imageUri = imageUri,
    )

fun DrinkSummary.toLocalDto(): DrinkSummaryLocalDto =
    DrinkSummaryLocalDto(
        id = id,
        name = name,
        imageUri = imageUri,
    )
