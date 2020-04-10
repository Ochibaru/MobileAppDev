package com.myfitnesstracker.networking

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.myfitnesstracker.dto.ComplexSearchWrapper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Deferred
import retrofit2.http.Query

interface RestApiService {

    @GET("/recipes/complexSearch?apiKey=1e607600ad2648b0bd74de02247c86ba&number=1")
    fun getComplexSearch(@Query("query") foodSearchTerm:String): Deferred<ComplexSearchWrapper>

    companion object {
        fun createCorService(): RestApiService {

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(RestApiService::class.java)
        }
    }
}