package com.myfitnesstracker.dto


import com.beust.klaxon.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComplexSearchResult (
    @Json(name="id")
    var id: String? = null,
    @Json(name="title")
    var title: String? = null,
    @Json(name="image")
    var image: String? = null,
    @Json(name="imageType")
    var imageType: String? = null
) {
    override fun toString(): String {
        return "id:" + id + "title:" + title + "image:" + image + "imagetype:" + imageType
    }
}