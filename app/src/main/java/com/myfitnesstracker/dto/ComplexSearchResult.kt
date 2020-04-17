package com.myfitnesstracker.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class ComplexSearchResult (
    //@field:Json(name="id")
    @SerializedName("id") @Expose
    var id: String? = "",
    //@field:Json(name="title")
    @SerializedName("title") @Expose
    var title: String? = "",
    //@field:Json(name="image")
    @SerializedName("image") @Expose
    var image: String? = "",
    //@field:Json(name="imageType")
    @SerializedName("imageType") @Expose
    var imageType: String? = ""
) {
    override fun toString(): String {
        return "id:" + id + "title:" + title + "image:" + image + "imagetype:" + imageType
    }
}