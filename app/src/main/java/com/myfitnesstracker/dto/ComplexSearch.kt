package com.myfitnesstracker.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComplexSearch (
    @Json(name="results")
    var results: List<ComplexSearchResult> = emptyList(),
    @Json(name="offset")
    var offset: String? = null,
    @Json(name="number")
    var number: String? = null,
    @Json(name="totalResults")
    var totalResults: String? = null
){
    override fun toString(): String {
        return "results:" + results
    }
}