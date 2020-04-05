package com.myfitnesstracker.dto

data class BMI(var weight: Double = 0.0, var height: Double = 0.0, var bmi: Double = 0.0, var bmiId: String = "") {
    override fun toString(): String {
        return "$bmi"
    }
}