package it.cocktail.data.datasource.network.client

object CocktailEndpoint {
    private const val BASE_PATH = "https://www.thecocktaildb.com"

    fun apiPath(apiKey: String) = "$BASE_PATH/api/json/v1/$apiKey"

    fun ingredientThumbnailPath(ingredientName: String) = "$BASE_PATH/images/ingredients/$ingredientName-small.png"

    fun drinkSummaryThumbnailPath(fullImagePath: String) = "$fullImagePath/small"
}
