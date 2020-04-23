package com.myfitnesstracker.ui.dto

data class BMI(var weight: Double = 0.0, var height: Double = 0.0, var bmi: Double = 0.0, var bmiId: String = "", var measurement: String = "", var age: Int = 0, var goal: Double = 0.0, var gender: String = "") {
    override fun toString(): String {
        return "$bmi"
    }
}