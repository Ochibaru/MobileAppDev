package com.myfitnesstracker.dto

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class Exercise(

    @SerializedName("user_input")
    @Expose
    var userInput: String? = null,
    @SerializedName("duration_min")
    @Expose
    var durationMin: Double? = null,
    @SerializedName("met")
    @Expose
    var met: Double? = null,
    @SerializedName("nf_calories")
    @Expose
    var nfCalories: Double? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null

)
