package com.myfitnesstracker.dao


import retrofit2.Call
import retrofit2.http.GET
import com.myfitnesstracker.dto.Result


interface ResultDAO {
    @GET( "https://api.spoonacular.com/recipes/275468/nutritionWidget.json?apiKey=1e607600ad2648b0bd74de02247c86ba")
    fun getResults() : Call<ArrayList<Result>>
}