package com.myfitnesstracker.dao

import com.myfitnesstracker.dto.Nutrition
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface INutritionDAO {

    @Headers("Content-Type: application/json")
    @GET( "complexSearch?apiKey=1e607600ad2648b0bd74de02247c86ba&number=1")
    fun getNutrition(@Query("query") nutrition:String) : Call<ArrayList<Nutrition>>
}