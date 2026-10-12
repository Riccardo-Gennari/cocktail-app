package it.cocktail.domain.usecase

import it.cocktail.domain.model.DrinkQuery
import it.cocktail.domain.model.DrinkSummary
import it.cocktail.domain.repository.DrinkSummaryRepository
import javax.inject.Inject

/**
 * Use case that encapsulates retrieving drinks based on a [DrinkQuery].
 *
 * @param repository The [DrinkSummaryRepository] used to fetch drinks data.
 */
class SearchDrinksUseCase
    @Inject
    constructor(
        private val repository: DrinkSummaryRepository,
    ) {
        /**
         * Searches for cocktails based on the specified [query].
         *
         * Blank [query] will immediately return an empty list.
         *
         * @param query The [DrinkQuery] specifying the search criteria.
         * @return A [Result] containing the list of matching [DrinkSummary] items, or an empty list if no matches are found.
         */
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
