package com.myfitnesstracker


import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


object RetrofitClientInstance {
    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://api.spoonacular.com/recipes/"
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val gson = GsonBuilder().create()

    private fun createGsonConverter(
        type: Type,
        typeAdapter: Any
    ): Converter.Factory? {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(type, typeAdapter)
        val gson = gsonBuilder.create()
        return GsonConverterFactory.create(gson)
    }

    fun getRetrofitInstance(
        type: Type?,
        typeAdapter: Any?
    ): Retrofit? {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(createGsonConverter(type!!, typeAdapter!!))
            .build()
    }

    val retrofitInstance: Retrofit?
        get() {
            //using if statement to check if retrofit is null otherwise return a value
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build()
            }
            return retrofit
        }
}
