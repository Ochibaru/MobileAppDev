package com.myfitnesstracker.service

import androidx.lifecycle.MutableLiveData
import com.myfitnesstracker.dao.INutritionSearchResultDAO
import com.myfitnesstracker.dto.NutritionSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.myfitnesstracker.RetrofitClientInstance

class NutritionSearchService {

    internal fun fetchNutritionSearchResults(nutrition: String) : MutableLiveData<ArrayList<NutritionSearchResult>> {
        var _nutritionSearch = MutableLiveData<ArrayList<NutritionSearchResult>>()
        val service = RetrofitClientInstance.retrofitInstance?.create(INutritionSearchResultDAO::class.java)
        val call = service?.getResults(nutrition)
        call?.enqueue(object : Callback<ArrayList<NutritionSearchResult>> {

            override fun onFailure(call: Call<ArrayList<NutritionSearchResult>>, t: Throwable) {
                // Need to complete failure response
            }

            override fun onResponse(
                call: Call<ArrayList<NutritionSearchResult>>,
                response: Response<ArrayList<NutritionSearchResult>>
            )
            {
                _nutritionSearch.value = response.body()
            }

        })
        return _nutritionSearch
    }
}