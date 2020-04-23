package com.myfitnesstracker.dao

import com.myfitnesstracker.dto.ComplexSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IComplexSearchDAO {

    // In URL the number=1 makes the call to the API only return one result
    @Headers("Content-Type: application/json")
    @GET( "complexSearch?apiKey=1e607600ad2648b0bd74de02247c86ba&number=1")
    fun getComplexResults (@Query("query") food:String) : Call<Any>

    fun getResults() : ComplexSearchResult
}