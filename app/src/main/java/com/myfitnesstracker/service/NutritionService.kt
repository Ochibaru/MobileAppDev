package com.myfitnesstracker.service

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.myfitnesstracker.dao.IComplexSearchDAO
import com.myfitnesstracker.dao.INutritionDAO
import com.myfitnesstracker.dto.Bad
import com.myfitnesstracker.dto.ComplexSearchResult
import com.myfitnesstracker.dto.Good
import com.myfitnesstracker.dto.Nutrition
import com.myfitnesstracker.ui.scripts.StringManipulator
import com.myfitnesstracker.ui.scripts.Time
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


object NutritionService {
    private lateinit var time: Time
    private val formatted = time.getLocalTimeAndConvertToString()
    private val stringManipulator = StringManipulator()

    /*
    *  Makes call to Spoonacular API and returns results to JSON objects.
    *  Results are than added to Firestore
    */
     fun doComplexSearch(foodItem: String, userEntry: String): ComplexSearchResult? {
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
                                responseBodyNutrition = stringManipulator.removeWhitespaceVitamin(responseBodyNutrition)
                                responseBodyNutrition = stringManipulator.removeWhitespaceSaturated(responseBodyNutrition)
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
                                firestore.collection("Login").document(userEntry).collection("Food").document("Date").collection(formatted).document(foodItem).set(nutrition)
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
            val badArrayItem = Bad()
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
            val goodArrayItem = Good()
            val itemBad = goodArrayJson.getJSONObject(i)
            goodArrayItem.title = itemBad["title"] as String
            goodArrayItem.amount = itemBad["amount"].toString()
            goodArrayItem.percentOfDailyNeeds = itemBad["percentOfDailyNeeds"] as Double
            goodArrayJsonList.add(goodArrayItem)
        }
        return goodArrayJsonList
    }
}