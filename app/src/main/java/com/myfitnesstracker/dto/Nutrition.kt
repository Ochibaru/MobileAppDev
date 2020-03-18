package com.myfitnesstracker.dto

import com.google.gson.annotations.SerializedName

data class Nutrition (
    @SerializedName("calories")var calories: String, @SerializedName("carbs") var carbs: String, @SerializedName("protien")var protein: String, @SerializedName("fat")var fat: String, @SerializedName("name")var name: String
)
{

}