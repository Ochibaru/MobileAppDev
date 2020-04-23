package com.myfitnesstracker.dto

data class ExerciseRequest(
     var query: String? = null,
     var gender: String? = null, // male or female
     var weight_kg: Double? = null,
     var height_cm: Double? = null,
     var age: Int? = null
)