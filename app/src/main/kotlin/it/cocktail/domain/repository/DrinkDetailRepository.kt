package it.cocktail.domain.repository

import it.cocktail.domain.model.DrinkDetail

interface DrinkDetailRepository {
    suspend fun getDetail(id: Int): Result<DrinkDetail?>
}
