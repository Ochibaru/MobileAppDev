Spoonacular API
    https://spoonacular.com/food-api/docs
    All calls must have API key in request URL: apiKey=1e607600ad2648b0bd74de02247c86ba
    Complex search results can be sorted, see https://spoonacular.com/food-api/docs#Recipe-Sorting-Options

To get nutrition information from spoonacular:
    Make call to API via complex search and item being queried, results will have an id for the food (results can contain multiple matching results)
        *results will also have a link for an image if wanting to use the photos of the food
    Take id from complex search and make a call to the API for nutrition widget
Example complex search API call (saved the results for testing purposes in file ComplexResultsPho.json)
    https://api.spoonacular.com/recipes/complexSearch?apiKey=1e607600ad2648b0bd74de02247c86ba&query=pho
Example nutrition widget API call (saved the results for testing purposes in file NutritionWidgetPhoResults.json)
    https://api.spoonacular.com/recipes/275468/nutritionWidget.json?apiKey=1e607600ad2648b0bd74de02247c86ba