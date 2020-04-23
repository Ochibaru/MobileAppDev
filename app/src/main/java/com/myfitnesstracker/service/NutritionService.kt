package com.myfitnesstracker.service

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.myfitnesstracker.dao.IComplexSearchDAO
import com.myfitnesstracker.dao.INutritionDAO
import com.myfitnesstracker.dto.Bad
import com.myfitnesstracker.dto.ComplexSearchResult
import com.myfitnesstracker.dto.Good
import com.myfitnesstracker.dto.Nutrition
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object NutritionService {

    /*
    *  Makes call to Spoonacular API and returns results to JSON objects.
    *  Results are than added to Firestore
    */
     fun doComplexSearch(foodItem: String): ComplexSearchResult? {
        var _search: ComplexSearchResult? = null
            val iComplexSearchDAO: IComplexSearchDAO? =
                RetrofitInstance.getRetrofitInstance()?.create()
            val serviceComplex = iComplexSearchDAO?.getComplexResults(foodItem)
            serviceComplex?.enqueue(object: Callback<Any>{
                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ){
                    var responseBodyComplex = response.body().toString()

                    if (responseBodyComplex.contains("https://")){
                        val target = "https://spoonacular.com/recipeImages/"
                        val replacement = ""
                        responseBodyComplex = responseBodyComplex.replace(target, replacement)
                    }
                    val objComplexJson = JSONObject(responseBodyComplex)
                    val resultsComplexArray: JSONArray = objComplexJson.getJSONArray("results")
                    val complexSearchResult = ComplexSearchResult().apply {
                        for (i in 0 until resultsComplexArray.length()){
                            val itemComplex = resultsComplexArray.getJSONObject(i)
                            id = itemComplex["id"].toString().substringBefore(".")
                            title = itemComplex["title"] as String?
                            image = itemComplex["image"] as String?
                            imageType = itemComplex["imageType"] as String?
                        }
                    }

                    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
                    val documentComplex = firestore.collection("Complex").document(complexSearchResult.id!!)
                    val nutritionId = complexSearchResult.id!!
                    val setComplex = documentComplex.set(complexSearchResult)


                    setComplex.addOnSuccessListener {
                        var _searchNutrition: Nutrition? = null
                        val iNutrition: INutritionDAO? =
                            RetrofitInstance.getRetrofitInstance()?.create()
                        val serviceNutrition = iNutrition?.getNutrition(nutritionId)
                        serviceNutrition?.enqueue(object: Callback<Any>{
                            override fun onResponse(
                                call: Call<Any>,
                                response: Response<Any>
                            ){
                                var responseBodyNutrition = response.body().toString()

                                responseBodyNutrition = removeNutritionSpaces(responseBodyNutrition)

                                val objNutritionJson = JSONObject(responseBodyNutrition)


                                val documentNutrition = firestore.collection("Food").document()

                                val nutrition = Nutrition().apply {
                                    calories = objNutritionJson.getString("calories")
                                    carbs = objNutritionJson.getString("carbs")
                                    fat = objNutritionJson.getString("fat")
                                    protein = objNutritionJson.getString("protein")

                                    var goodArrayResult = arrayListOf<Good>()
                                    var badArrayResult = arrayListOf<Bad>()

                                    badArrayResult = getBadNutritionStringsAsArray(badArrayResult, responseBodyNutrition)
                                    goodArrayResult = getGoodNutritionStringsAsArray(goodArrayResult, responseBodyNutrition)
                                    bad = badArrayResult
                                    good = goodArrayResult

                                    nutritionDocumentId = documentNutrition.id

                                }
                                val current = LocalDateTime.now()
                                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                val formatted = current.format(formatter)
                                firestore.collection("Food").document("Date").collection(formatted).document(foodItem).set(nutrition)

                                _searchNutrition = nutrition
                            }

                            override fun onFailure(call: Call<Any>, t: Throwable) {
                                Log.d("Error","Error at Retrofit thread enqueue in Nutrition Service for iNutrition call.")
                            }
                        })
                        return@addOnSuccessListener

                    }

                    _search = complexSearchResult
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.d("Error","Error at Retrofit thread enqueue in Nutrition Service for iComplexSearchDAO call.")
                }
            })
         return _search
    }

    /*
    * Takes the raw string from the API call and removes spaces from key-value pairs.
    * As Json objects are not allowed to have spaces in their values.
    *
     */
    fun removeNutritionSpaces(nutritionResultString: String): String {
        var nutritionString = nutritionResultString

        if(nutritionString.contains("Vitamin A")){
            val target = "Vitamin A"
            val replacement = "VitaminA"
            nutritionString = nutritionString.replace(target, replacement)
        }
        if(nutritionString.contains("Vitamin B1")){
            val target = "Vitamin B1"
            val replacement = "VitaminB1"
            nutritionString = nutritionString.replace(target, replacement)
        }
        if(nutritionString.contains("Vitamin B2")){
            val target = "Vitamin B2"
            val replacement = "VitaminB2"
            nutritionString = nutritionString.replace(target, replacement)
        }
        if(nutritionString.contains("Vitamin B3")){
            val target = "Vitamin B3"
            val replacement = "VitaminB3"
            nutritionString = nutritionString.replace(target, replacement)
        }
        if(nutritionString.contains("Vitamin B5")){
            val target = "Vitamin B5"
            val replacement = "VitaminB5"
            nutritionString = nutritionString.replace(target, replacement)
        }
        if(nutritionString.contains("Vitamin B6")){
            val target = "Vitamin B6"
            val replacement = "VitaminB6"
            nutritionString = nutritionString.replace(target, replacement)
        }
        if(nutritionString.contains("Vitamin B7")){
            val target = "Vitamin B7"
            val replacement = "VitaminB7"
            nutritionString = nutritionString.replace(target, replacement)
        }
        if(nutritionString.contains("Vitamin B9")){
            val target = "Vitamin B9"
            val replacement = "VitaminB9"
            nutritionString = nutritionString.replace(target, replacement)
        }
        if(nutritionString.contains("Vitamin B12")){
            val target = "Vitamin B12"
            val replacement = "VitaminB12"
            nutritionString = nutritionString.replace(target, replacement)
        }
        if(nutritionString.contains("Vitamin C")){
            val target = "Vitamin C"
            val replacement = "VitaminC"
            nutritionString = nutritionString.replace(target, replacement)
        }
        if(nutritionString.contains("Vitamin D")){
            val target = "Vitamin D"
            val replacement = "VitaminD"
            nutritionString = nutritionString.replace(target, replacement)
        }
        if(nutritionString.contains("Vitamin E")){
            val target = "Vitamin E"
            val replacement = "VitaminE"
            nutritionString = nutritionString.replace(target, replacement)
        }
        if(nutritionString.contains("Vitamin K")){
            val target = "Vitamin K"
            val replacement = "VitaminK"
            nutritionString = nutritionString.replace(target, replacement)
        }
        if(nutritionString.contains("Saturated Fat")){
            val target = "Saturated Fat"
            val replacement = "SaturatedFat"
            nutritionString = nutritionString.replace(target, replacement)
        }
        return nutritionString
    }

    /*
    * Function to parse through the Bad DTO and add it to array
    */
    @Throws(Exception::class)
    fun getBadNutritionStringsAsArray(
        badArrayJsonList: ArrayList<Bad>,
        rawJson: String
    ): ArrayList<Bad> {
        val objNutritionJson = JSONObject(rawJson)
        val badArrayJson: JSONArray = objNutritionJson.getJSONArray("bad")

        for (i in 0 until badArrayJson.length()){
            val badArrayItem: Bad = Bad()
            val itemBad = badArrayJson.getJSONObject(i)
            badArrayItem.title = itemBad["title"] as String
            badArrayItem.amount = itemBad["amount"].toString()
            badArrayItem.percentOfDailyNeeds = itemBad["percentOfDailyNeeds"] as Double
            badArrayJsonList.add(badArrayItem)
        }

        return badArrayJsonList
    }

    /*
    * Function to parse through the Good DTO and add it to array
    */
    @Throws(Exception::class)
    fun getGoodNutritionStringsAsArray(
        goodArrayJsonList: ArrayList<Good>,
        rawJson: String
    ): ArrayList<Good> {
        val objNutritionJson = JSONObject(rawJson)
        val goodArrayJson: JSONArray = objNutritionJson.getJSONArray("good")

        for (i in 0 until goodArrayJson.length()){
            val goodArrayItem: Good = Good()
            val itemBad = goodArrayJson.getJSONObject(i)
            goodArrayItem.title = itemBad["title"] as String
            goodArrayItem.amount = itemBad["amount"].toString()
            goodArrayItem.percentOfDailyNeeds = itemBad["percentOfDailyNeeds"] as Double
            goodArrayJsonList.add(goodArrayItem)
        }

        return goodArrayJsonList
    }

}