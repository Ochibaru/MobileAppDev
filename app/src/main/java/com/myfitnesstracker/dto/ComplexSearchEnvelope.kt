package com.myfitnesstracker.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ComplexSearchEnvelope<T>(@SerializedName("results") @Expose val results: T)
