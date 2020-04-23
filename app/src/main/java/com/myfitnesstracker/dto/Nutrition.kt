package com.myfitnesstracker.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Nutrition(
    @SerializedName("calories")
    @Expose
    var calories: String? = null,
    @SerializedName("carbs")
    @Expose
    var carbs: String? = null,
    @SerializedName("fat")
    @Expose
    var fat: String? = null,
    @SerializedName("protein")
    @Expose
    var protein: String? = null,
    @SerializedName("bad")
    @Expose
    var bad: List<Bad>? = null,
    @SerializedName("good")
    @Expose
    var good: List<Good>? = null,
    @SerializedName("expires")
    @Expose
    var expires: Int? = null,
    @SerializedName("nutritionId")
    @Expose
    var nutritionDocumentId: String? = null
)

data class Bad (
    @SerializedName("title")
    @Expose
    var title: String? = null,
    @SerializedName("amount")
    @Expose
    var amount: String? = null,
    @SerializedName("indented")
    @Expose
    var indented: Boolean? = null,
    @SerializedName("percentOfDailyNeeds")
    @Expose
    var percentOfDailyNeeds: Double? = null
)

data class Good (
    @SerializedName("title")
    @Expose
    var title: String? = null,
    @SerializedName("amount")
    @Expose
    var amount: String? = null,
    @SerializedName("indented")
    @Expose
    var indented: Boolean? = null,
    @SerializedName("percentOfDailyNeeds")
    @Expose
    var percentOfDailyNeeds: Double? = null
)
