package com.myfitnesstracker.dao

import retrofit2.Call
import retrofit2.http.GET
import com.myfitnesstracker.dto.NutritionSearchResultDTO
import retrofit2.http.Headers
import retrofit2.http.Query


interface INutritionSearchResultDAO {

    // In URL the number=1 makes the call to the API only return one result
    @Headers("Content-Type: application/json")
    @GET( "complexSearch?apiKey=1e607600ad2648b0bd74de02247c86ba&number=1")
    fun getResults(@Query("query") food:String) : Call<ArrayList<NutritionSearchResultDTO>>
}