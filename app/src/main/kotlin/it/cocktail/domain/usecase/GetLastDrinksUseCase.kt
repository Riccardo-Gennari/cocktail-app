package it.cocktail.domain.usecase

import it.cocktail.domain.model.DrinkSummary
import it.cocktail.domain.repository.DrinkSummaryRepository
import javax.inject.Inject

/**
 * Use case that encapsulates retrieving the last drinks queried by the user.
 *
 * @param repository The [DrinkSummaryRepository] used to fetch drinks data.
 */
class GetLastDrinksUseCase
    @Inject
    constructor(
        private val repository: DrinkSummaryRepository,
    ) {
        /**
         * Returns the last list of [DrinkSummary] queried by the user.
         * @return a [Result] containing the list of [DrinkSummary].
         */
        suspend operator fun invoke(): Result<List<DrinkSummary>> = repository.getStoredDrinks()
    }
