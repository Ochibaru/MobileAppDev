package com.myfitnesstracker.dto

import com.google.gson.annotations.SerializedName

data class NutritionSearchResultDTO (
  @SerializedName("id")var id: String = "", @SerializedName("title")var title: String = "", @SerializedName("image")var image: String = "", @SerializedName("imageType")var imageType: String = ""
)
{

  override fun toString(): String {
    return "id:" + id + "title:" + title + "image:" + image + "imagetype:" + imageType
  }
}