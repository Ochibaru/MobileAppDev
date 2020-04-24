package com.myfitnesstracker.service

import androidx.lifecycle.MutableLiveData
import com.myfitnesstracker.dao.INutritionDAO
import com.myfitnesstracker.dto.Nutrition
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.myfitnesstracker.RetrofitClientInstance

class NutritionService {
    fun fetch(nutrition: String): MutableLiveData<ArrayList<Nutrition>> {
        //initiating variable nutrition to ArrayList
        var _nutrition = MutableLiveData<ArrayList<Nutrition>>()
        //initiation a variable with retrofitInstance
        val service = RetrofitClientInstance.retrofitInstance?.create(INutritionDAO::class.java)
        val call = service?.getNutrition(nutrition)
        //calling enqueue
        call?.enqueue(object: Callback<ArrayList<Nutrition>> {
            /*
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            //overriding a function onFailure
            override fun onFailure(call: Call<ArrayList<Nutrition>>, t: Throwable) {

                return
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