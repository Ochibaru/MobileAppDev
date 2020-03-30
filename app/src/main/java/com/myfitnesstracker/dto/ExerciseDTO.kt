package com.myfitnesstracker.dto
import com.google.gson.annotations.SerializedName

data class ExerciseDTO(
    @SerializedName("tag_id") var tag_id: String,
    @SerializedName("user_input") var user_input: String,
    @SerializedName("duration_min") var duration_min: String,
    @SerializedName("met") var met: String,
    @SerializedName("nf_calories") var nf_calories: String
    )