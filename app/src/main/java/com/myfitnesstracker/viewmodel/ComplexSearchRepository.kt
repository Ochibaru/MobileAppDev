package com.myfitnesstracker.viewmodel

import androidx.lifecycle.MutableLiveData
import com.myfitnesstracker.dto.ComplexSearch
import com.myfitnesstracker.dto.ComplexSearchResult
import com.myfitnesstracker.networking.RestApiService
import kotlinx.coroutines.*
import retrofit2.HttpException


class ComplexSearchRepository() {

    private var food = mutableListOf<ComplexSearchResult>()
    private var mutableLiveData = MutableLiveData<ComplexSearchResult>()
    val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    private val thisApiCorService by lazy {
        RestApiService.createCorService()
    }

    fun getMutableLiveData(foodSearchTerm:String):MutableLiveData<List<ComplexSearchResult>> {
        coroutineScope.launch {
            val request = thisApiCorService.getComplexSearch(foodSearchTerm)
            withContext(Dispatchers.Main) {
                try {

                    val response = request.await()
                    val mComplexWrapper = response;
                    if (mComplexWrapper != null && mComplexWrapper.blog != null) {
                        food = mComplexWrapper.blog as MutableList<ComplexSearchResult>;
                        mutableLiveData.value=food;
                    }

                } catch (e: HttpException) {
                    // Log exception //

                } catch (e: Throwable) {
                    // Log error //)
                }
            }
        }
        return mutableLiveData;
    }
}