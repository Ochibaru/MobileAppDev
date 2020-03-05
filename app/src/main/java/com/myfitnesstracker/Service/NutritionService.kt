package com.myfitnesstracker.Service

import androidx.lifecycle.MutableLiveData
import com.myfitnesstracker.dto.Nutrition

class NutritionService {
    fun fetchNutritionInfo(): MutableLiveData<ArrayList<Nutrition>> {
        var _nutrition = MutableLiveData<ArrayList<Nutrition>>()

        return _nutrition
    }

}