package com.myfitnesstracker.service

import androidx.lifecycle.MutableLiveData
import com.myfitnesstracker.dao.NutritionDAO
import com.myfitnesstracker.dto.Nutrition
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ui.main.RetrofitClientInstance

class NutritionService {
    fun fetch(): MutableLiveData<ArrayList<Nutrition>> {
        //initiating variable nutrition to ArrayList
        var _nutrition = MutableLiveData<ArrayList<Nutrition>>()
        //initiationg a variable with retrofitInstance
        val service = RetrofitClientInstance.retrofitInstance?.create(NutritionDAO::class.java)
        val call = service?.getNutrition()
        //calling enqueue
        call?.enqueue(object: Callback<ArrayList<Nutrition>> {
            /*
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            //overriding a function onFailure
            override fun onFailure(call: Call<ArrayList<Nutrition>>, t: Throwable) {
                val j = 1 + 1
                val i = 1 + 1
            }


            /* Invoked for a received HTTP response.
            *
            *
            * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
            * Call [Response.isSuccessful] to determine if the response indicates success.
            */
            //overriding a function onResponse
            override fun onResponse(
                call: Call<ArrayList<Nutrition>>,
                response: Response<ArrayList<Nutrition>>
            ) {
                _nutrition.value = response.body()
            }

        })
        //returning the value
        return _nutrition
    }
}