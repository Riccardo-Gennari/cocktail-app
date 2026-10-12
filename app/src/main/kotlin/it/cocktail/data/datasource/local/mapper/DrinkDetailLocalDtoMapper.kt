package it.cocktail.data.datasource.local.mapper

import it.cocktail.data.datasource.local.dto.DrinkDetailLocalDto
import it.cocktail.data.datasource.local.dto.IngredientLocalDto
import it.cocktail.domain.model.DrinkDetail
import it.cocktail.domain.model.Ingredient

/**
 * Maps [DrinkDetailLocalDto] to a domain [DrinkDetail].
 */
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

/**
 * Maps [DrinkDetail] to a [DrinkDetailLocalDto] for local storage.
 */
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

/**
 * Maps [IngredientLocalDto] to a domain [Ingredient].
 */
fun IngredientLocalDto.toDomain(): Ingredient =
    Ingredient(
        name = name,
        measure = measure,
        imageUri = imageUri,
    )

/**
 * Maps [Ingredient] to a [IngredientLocalDto] for local storage.
 */
fun Ingredient.toLocalDto(): IngredientLocalDto =
    IngredientLocalDto(
        name = name,
        measure = measure,
        imageUri = imageUri,
    )
