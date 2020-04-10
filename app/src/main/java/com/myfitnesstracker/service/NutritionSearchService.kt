package com.myfitnesstracker.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.myfitnesstracker.RetrofitClientInstance
import com.myfitnesstracker.dao.INutritionSearchResultDAO
import com.myfitnesstracker.dto.NutritionSearchResultDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NutritionSearchService {

    internal fun fetchNutritionSearchResults(nutrition: String) : MutableLiveData<ArrayList<NutritionSearchResultDTO>> {
        var _nutritionSearch = MutableLiveData<ArrayList<NutritionSearchResultDTO>>()
        val service = RetrofitClientInstance.retrofitInstance?.create(INutritionSearchResultDAO::class.java)
        val call = service?.getResults(nutrition)
        call?.enqueue(object : Callback<ArrayList<NutritionSearchResultDTO>> {

            override fun onFailure(call: Call<ArrayList<NutritionSearchResultDTO>>, t: Throwable) {
                // Need to complete failure response
            }

            override fun onResponse(
                call: Call<ArrayList<NutritionSearchResultDTO>>,
                response: Response<ArrayList<NutritionSearchResultDTO>>
            )
            {
                _nutritionSearch.value = response.body()
            }

        })
        Log.d("test", "Results: " + _nutritionSearch)

        return _nutritionSearch
    }

    //var parsedNutritionSearchResults : MutableLiveData<ArrayList<NutritionSearchResultDTO>> = TODO()

    internal fun fetchNutritionSearchResults(filepath: String, usingFilepath: String) : MutableLiveData<ArrayList<NutritionSearchResultDTO>> {
        var _nutritionSearch = MutableLiveData<ArrayList<NutritionSearchResultDTO>>()



        return _nutritionSearch
    }

}