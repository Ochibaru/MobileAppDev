package com.myfitnesstracker

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstanceNutritionix {
    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://trackapi.nutritionix.com/"

    val retrofitInstance: Retrofit?
        get() {
            //using if statement to check if retrofit is null otherwise return a value
            if (RetrofitClientInstanceNutritionix.retrofit == null) {
                RetrofitClientInstanceNutritionix.retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return RetrofitClientInstanceNutritionix.retrofit
        }
}