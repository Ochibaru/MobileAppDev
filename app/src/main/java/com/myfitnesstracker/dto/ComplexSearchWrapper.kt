package com.myfitnesstracker.dto

import com.squareup.moshi.Json

data class ComplexSearchWrapper (
    @Json(name="data")
    var blog: MutableList<ComplexSearch>? = null,
    @Json(name="error")
    var error: Boolean? = null,
    @Json(name="message")
    var message: String? = null,
    @Json(name="status")
    var status: String? = null
)