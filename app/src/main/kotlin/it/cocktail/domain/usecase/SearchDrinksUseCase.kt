package it.cocktail.domain.usecase

import it.cocktail.domain.model.DrinkQuery
import it.cocktail.domain.model.DrinkSummary
import it.cocktail.domain.repository.DrinkSummaryRepository
import javax.inject.Inject

class SearchDrinksUseCase
    @Inject
    constructor(
        private val repository: DrinkSummaryRepository,
    ) {
        suspend operator fun invoke(query: DrinkQuery): Result<List<DrinkSummary>> =
            when (query) {
                is DrinkQuery.ByName -> {
                    val trimmed = query.name.trim()
                    if (trimmed.isEmpty()) {
                        Result.success(emptyList())
                    } else {
                        repository
                            .searchByName(trimmed)
                            .onSuccess { repository.storeDrinks(it) }
                    }
                }

                is DrinkQuery.ByIngredient -> {
                    val trimmed = query.ingredient.trim()
                    if (trimmed.isEmpty()) {
                        Result.success(emptyList())
                    } else {
                        repository
                            .searchByIngredient(trimmed)
                            .onSuccess { repository.storeDrinks(it) }
                    }
                }
            }
    }
