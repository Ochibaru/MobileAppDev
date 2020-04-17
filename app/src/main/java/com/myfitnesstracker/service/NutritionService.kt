package com.myfitnesstracker.service

import android.content.ClipData.Item
import android.util.Log
import com.myfitnesstracker.RetrofitClientInstance
import com.myfitnesstracker.RetrofitClientInstance.getRetrofitInstance
import com.myfitnesstracker.RetrofitInstance
import com.myfitnesstracker.dao.IComplexSearchDAO
import com.myfitnesstracker.dto.ComplexSearchResult
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.*
import java.io.IOException


object NutritionService {


     fun doComplexSearch(foodItem: String): String {
        /* val service = RetrofitClientInstance.retrofitInstance?.create(IComplexSearchDAO::class.java)
        val call = service?.getComplexResults(foodItem)?.awaitResponse()
        val resultsJsonResponse = call!!.body()
        //var result = ComplexSearchResult()
        //var listed: List<ComplexSearchResult>  = ArrayToListAdapter.fromJson
var test: String = ""
        test = resultsJsonResponse.toString()

         */

        var _search = ""
            val iComplexSearchDAO: IComplexSearchDAO? =
                RetrofitInstance.getRetrofitInstance()?.create()
            val service = iComplexSearchDAO?.getComplexResults(foodItem)
            service?.enqueue(object: Callback<Any>{
                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ){
                    val test = response.body().toString()
                    _search = test
                    //val test = response.body()!!.title.toString()
                    //_search = response.body()!!
                    Log.d("ns", test)
                    Log.d("tr", response.body().toString())
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    print("Error at Retrofit thread enqueue in Nutrition Service.")
                }
            })
         return _search
    }


}
