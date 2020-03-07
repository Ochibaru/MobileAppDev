package ui.main

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {
    //initiating private variable to null
    private var retrofit: Retrofit? = null
    private val BASE_URL = "http://jsonservices.com/cgi-bin/"

    val retrofitInstance: Retrofit?
        //using get method
        get() {
            //using if statement to check if retrofit is null otherwise return a value
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            //returning a value
            return retrofit
        }
}