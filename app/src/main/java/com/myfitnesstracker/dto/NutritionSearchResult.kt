package com.myfitnesstracker.dto

import com.google.gson.annotations.SerializedName

data class NutritionSearchResult (
  @SerializedName("id")var id: String, @SerializedName("title")var title: String, @SerializedName("image")var image: String, @SerializedName("imageType")var imageType: String
)
{

}