package it.cocktail.domain.usecase

import it.cocktail.domain.model.DrinkSummary
import it.cocktail.domain.repository.DrinkSummaryRepository
import javax.inject.Inject

class GetLastDrinksUseCase
    @Inject
    constructor(
        private val repository: DrinkSummaryRepository,
    ) {
        suspend operator fun invoke(): Result<List<DrinkSummary>> = repository.getStoredDrinks()
    }
