package it.cocktail.data.datasource.local.mapper

import it.cocktail.data.datasource.local.dto.DrinkDetailLocalDto
import it.cocktail.data.datasource.local.dto.IngredientLocalDto
import it.cocktail.domain.model.DrinkDetail
import it.cocktail.domain.model.Ingredient

fun DrinkDetailLocalDto.toDomain(): DrinkDetail =
    DrinkDetail(
        id = id,
        name = name,
        category = category,
        alcoholic = alcoholic,
        glass = glass,
        instructions = instructions,
        imageUri = imageUri,
        ingredients = ingredients.map { it.toDomain() },
    )

fun DrinkDetail.toLocalDto(): DrinkDetailLocalDto =
    DrinkDetailLocalDto(
        id = id,
        name = name,
        category = category,
        alcoholic = alcoholic,
        glass = glass,
        instructions = instructions,
        imageUri = imageUri,
        ingredients = ingredients.map { it.toLocalDto() },
    )

fun IngredientLocalDto.toDomain(): Ingredient =
    Ingredient(
        name = name,
        measure = measure,
        imageUri = imageUri,
    )

fun Ingredient.toLocalDto(): IngredientLocalDto =
    IngredientLocalDto(
        name = name,
        measure = measure,
        imageUri = imageUri,
    )
