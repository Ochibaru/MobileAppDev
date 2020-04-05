package com.myfitnesstracker.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.myfitnesstracker.dto.Exercise
import com.myfitnesstracker.dto.Nutrition
import com.myfitnesstracker.dto.NutritionSearchResult
import com.myfitnesstracker.service.ExerciseService
import com.myfitnesstracker.service.NutritionSearchService
import com.myfitnesstracker.service.NutritionService
import com.myfitnesstracker.dto.BMI

class MainViewModel : ViewModel() {

   private var mainFragment: MainFragment = MainFragment()
   //private var storageReference = FirebaseStorage.getInstance().reference
   private var _bmis: MutableLiveData<ArrayList<BMI>> = MutableLiveData<ArrayList<BMI>>()
   private var _exercises = MutableLiveData<ArrayList<Exercise>>()
   private var _nutrition = MutableLiveData<ArrayList<Nutrition>>()
   private var _nutritionSearch = MutableLiveData<ArrayList<NutritionSearchResult>>()
   private lateinit var firestore: FirebaseFirestore
   var exerciseService: ExerciseService = ExerciseService()
   var nutritionService: NutritionService = NutritionService()
   var nutritionSearchResultService: NutritionSearchService = NutritionSearchService()

   /*    Commenting out till error is fixed with Firebase
   init {
      firestore = FirebaseFirestore.getInstance()
      firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
      listenToBMI()
   }

   private fun listenToBMI() {
      firestore.collection("BMI").addSnapshotListener{
         snapshot, e ->
         if (e != null){
            Log.w(TAG, "Listen Failed", e)
            return@addSnapshotListener
         }
         if (snapshot != null){
            val allBMIs = ArrayList<BMI>()
            val documents = snapshot.documents
            documents.forEach{
               val bmi = it.toObject(BMI::class.java)
               if (bmi != null){
                  allBMIs.add(bmi!!)
               }
            }
            _bmis.value = allBMIs
         }
      }
   }

   fun save(bmi: BMI) {
      val document = firestore.collection("BMI").document()
         bmi.bmiId = document.id
         val set = document.set(bmi)
         set.addOnSuccessListener {
            Log.d("Firebase", "Calculation Succeeded")
         }
         .addOnFailureListener{
            Log.d("Firebase","Calculate Failed")
         }
   }

    */

   init {
      fetchNutritionSearchResults("pho")
   }

   internal var bmis:MutableLiveData<ArrayList<BMI>>
      get() {return _bmis}
      set(value) {_bmis = value}


    //var userBMI = mainFragment.calculateBMI()
    fun saveBMI(bmi: String){
       var calculatedbmi = mainFragment.saveBMI(bmi)
    }

    fun fetchUserInputBMI(weight: Double, height: Double) {
      var userInputBMI = mainFragment.fetchUserInputBMI(weight, height)
   }


   fun fetchExerciseInfo(exerciseName:String) {
      _exercises = exerciseService.fetchExercise(exerciseName)
   }

   fun fetchNutritionSearchResults(nutritionSearch:String){
      _nutritionSearch = nutritionSearchResultService.fetchNutritionSearchResults(nutritionSearch)
   }

   fun fetchNutritionInfo(nutritionID:String){
      _nutrition = nutritionService.fetch(nutritionID)
   }

   var exercises:MutableLiveData<ArrayList<Exercise>>
      get() { return _exercises}
      set(value) {_exercises = value}

   var nutritionSearch:MutableLiveData<ArrayList<NutritionSearchResult>>
      get() {return _nutritionSearch}
      set(value) {_nutritionSearch = value}

   var nutrition:MutableLiveData<ArrayList<Nutrition>>
      get() { return _nutrition}
      set(value) {_nutrition = value}

}

