package com.myfitnesstracker.service

import java.util.*
import com.myfitnesstracker.ui.dto.BMI

class BMIService {
    var _height: Double = 0.0;
    var _weight: Double = 0.0;
    var _bmi: Double = 0.0;

    internal fun fetchUserInputBMI(weight: Double, height: Double): Double.Companion {
        _height =  height
        _weight = weight

        _bmi = _height / _weight

        return Double
    }

    fun getBMI(h : Double, w : Double): Double.Companion {
        _height =  h
        _weight = w

        _bmi = _height / _weight

        return Double
    }
}