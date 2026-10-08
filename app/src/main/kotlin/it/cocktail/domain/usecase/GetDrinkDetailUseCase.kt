package it.cocktail.domain.usecase

import it.cocktail.domain.model.DrinkDetail
import it.cocktail.domain.repository.DrinkDetailRepository
import javax.inject.Inject

class GetDrinkDetailUseCase
    @Inject
    constructor(
        private val repository: DrinkDetailRepository,
    ) {
        suspend operator fun invoke(id: Int): Result<DrinkDetail?> = repository.getDetail(id)
    }
