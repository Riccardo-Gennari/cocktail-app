package it.cocktail.domain.usecase

import it.cocktail.domain.model.DrinkDetail
import it.cocktail.domain.repository.DrinkDetailRepository
import javax.inject.Inject

/**
 * Use case that encapsulates retrieving the full recipe for a drink.
 *
 * @param repository The [DrinkDetailRepository] used to fetch drinks data.
 */
class GetDrinkDetailUseCase
    @Inject
    constructor(
        private val repository: DrinkDetailRepository,
    ) {
        /**
         * Fetches a [DrinkDetail] based on [id].
         * @param id the ID of the requested drink
         * @param forceRefresh if true, requests fresh data from source of truth, ignoring any local cache.
         * @return a [Result] containing the [DrinkDetail] matching [id], or null if none was found.
         */
        suspend operator fun invoke(
            id: Int,
            forceRefresh: Boolean = false,
        ): Result<DrinkDetail?> = repository.getDetail(id, forceRefresh)
    }
