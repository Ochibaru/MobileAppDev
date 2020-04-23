package com.myfitnesstracker.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ComplexSearchResult (
    @SerializedName("id")
    @Expose
    var id: String? = "",
    @SerializedName("title")
    @Expose
    var title: String? = "",
    @SerializedName("image")
    @Expose
    var image: String? = "",
    @SerializedName("imageType")
    @Expose
    var imageType: String? = ""
) {
    override fun toString(): String {
        return "id:" + id + "title:" + title + "image:" + image + "imagetype:" + imageType
    }
}