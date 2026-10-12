package it.cocktail.data.datasource.network.client

/**
 * Provides endpoints for CocktailDB network API.
 */
object CocktailEndpoint {
    private const val BASE_PATH = "https://www.thecocktaildb.com"

    /** Returns the endpoint for CocktailDB API calls using [apiKey] for auth. */
    fun apiPath(apiKey: String) = "$BASE_PATH/api/json/v1/$apiKey"

    /** Returns the endpoint for getting the thumbnail image associated to a cocktail ingredient named [ingredientName]. */
    fun ingredientThumbnailPath(ingredientName: String) = "$BASE_PATH/images/ingredients/$ingredientName-small.png"

    /** Returns the endpoint for getting the thumbnail version of the cocktail summary image with path [fullImagePath]. */
    fun drinkSummaryThumbnailPath(fullImagePath: String) = "$fullImagePath/small"
}
