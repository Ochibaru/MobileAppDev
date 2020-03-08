package com.myfitnesstracker.service

import BMI_DAO
import java.util.*
import com.myfitnesstracker.ui.dto.BMI

abstract class BMIService: BMI_DAO {

    internal fun fetchUserInputBMI(weight: Double, height: Double){

    }

    override fun getBMI(h : Double, w : Double): Double.Companion {
        return Double
    }

}