package com.myfitnesstracker.dao


import com.myfitnesstracker.dto.Nutrition
import retrofit2.Call
import retrofit2.http.GET

interface NutritionDAO {

    @GET( "https://api.spoonacular.com/recipes/complexSearch?apiKey=1e607600ad2648b0bd74de02247c86ba&query=pho")
    fun getNutrition() : Call<ArrayList<Nutrition>>
}