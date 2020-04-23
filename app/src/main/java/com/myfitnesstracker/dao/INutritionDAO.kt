package com.myfitnesstracker.dao

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface INutritionDAO {

    @Headers("Content-Type: application/json")
    @GET( "{id}/nutritionWidget.json?apiKey=1e607600ad2648b0bd74de02247c86ba&number=1")
    fun getNutrition(@Path("id") nutritionId:String) : Call<Any>
}