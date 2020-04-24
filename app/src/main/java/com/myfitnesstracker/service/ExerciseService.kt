package com.myfitnesstracker.service

import IExerciseDAO
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.myfitnesstracker.dto.Exercise
import com.myfitnesstracker.dto.ExerciseRequest
import com.myfitnesstracker.ui.scripts.Time
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

object ExerciseService {
    private lateinit var time: Time
    private val formatted = time.getLocalTimeAndConvertToString()
    /*
    *  Makes call to Nutritionix API and returns results to JSON objects.
    *  Results are than added to Firestore
    */
    fun fetchExercise(exercise: ExerciseRequest, userEntry: String): Exercise? {
        var _exercises: Exercise? = null
        val iExerciseDAO: IExerciseDAO? =
            RetrofitInstanceNutritionix.getRetrofitInstance()?.create()
        val service = iExerciseDAO?.getExercise(exercise)
        service?.enqueue(object: Callback<Any>{
            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ){
                // The API returns a lot of information that isn't needed and will throw errors for special characters
                val responseExerciseCall = response.body().toString()
                var editedResponse = responseExerciseCall.substringBefore(", photo=")
                editedResponse = "$editedResponse}]}"

                var exerciseInput = ""
                Log.d("EService", editedResponse)

                val objExerciseJson = JSONObject(editedResponse)
                val resultsExerciseArray: JSONArray = objExerciseJson.getJSONArray("exercises")
                val exerciseResponse = Exercise().apply {
                    for (i in 0 until resultsExerciseArray.length()){
                        val itemExercise = resultsExerciseArray.getJSONObject(i)
                        userInput = itemExercise["user_input"]  as String?
                        exerciseInput = userInput.toString()
                        durationMin = itemExercise["duration_min"] as Double?
                        nfCalories = itemExercise["nf_calories"] as Double?
                        met = itemExercise["met"] as Double?
                    }
                }
                val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
                firestore.collection("Login").document(userEntry).collection("Exercise").document("Date").collection(formatted).document(exerciseInput).set(exerciseResponse)
                _exercises = exerciseResponse
            }
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("Error","Error at Retrofit thread enqueue in Exercise Service for iExerciseDAO call.")
            }
        })
        return _exercises
    }

}




