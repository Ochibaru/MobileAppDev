package com.myfitnesstracker.service

import IExerciseDAO
import androidx.lifecycle.MutableLiveData
import com.myfitnesstracker.RetrofitClientInstanceNutritionix
import com.myfitnesstracker.dto.ExerciseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExerciseService {

    internal fun fetchExercise(exercise: String) : MutableLiveData<ArrayList<ExerciseDTO>> {
        var _exercises = MutableLiveData<ArrayList<ExerciseDTO>>()
        val service = RetrofitClientInstanceNutritionix.retrofitInstance?.create(IExerciseDAO::class.java)
        val call = service?.getExercise(exercise)
        call?.enqueue(object : Callback<ArrayList<ExerciseDTO>> {

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<ArrayList<ExerciseDTO>>, t: Throwable) {
                // Need to complete failure response
            }

            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(
                call: Call<ArrayList<ExerciseDTO>>,
                response: Response<ArrayList<ExerciseDTO>>
            )
            {
                _exercises.value = response.body()
            }
        })
        return _exercises
    }
}