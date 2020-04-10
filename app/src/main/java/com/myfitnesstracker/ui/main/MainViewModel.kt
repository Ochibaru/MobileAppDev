package com.myfitnesstracker.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beust.klaxon.Klaxon
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import com.myfitnesstracker.dto.*
import com.myfitnesstracker.service.ExerciseService
import com.myfitnesstracker.service.NutritionComplexSearchService
import com.myfitnesstracker.service.NutritionSearchService
import com.myfitnesstracker.service.NutritionService
import com.myfitnesstracker.ui.dto.BMI
import com.myfitnesstracker.viewmodel.ComplexSearchRepository
import java.io.StringReader

class MainViewModel : ViewModel() {

   private var mainFragment: MainFragment = MainFragment()
   //private var storageReference = FirebaseStorage.getInstance().reference
   private var _bmis: MutableLiveData<ArrayList<BMI>> = MutableLiveData<ArrayList<BMI>>()
   private var _exercises = MutableLiveData<ArrayList<ExerciseDTO>>()
   private var _nutrition = MutableLiveData<ArrayList<Nutrition>>()
   private var _nutritionSearch = MutableLiveData<ArrayList<NutritionSearchResultDTO>>()
   private var _complex = MutableLiveData<ArrayList<NutritionSearchResultDTO>>()
   private lateinit var firestore: FirebaseFirestore
   var exerciseService: ExerciseService = ExerciseService()
   var nutritionService: NutritionService = NutritionService()
   var nutritionSearchResultService: NutritionSearchService = NutritionSearchService()
   var nutritionComplexSearchService: NutritionComplexSearchService = NutritionComplexSearchService()


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
      // fetchNutritionSearchResults("pho")
   }


   //val result = Klaxon().parse<>


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

   var exercises:MutableLiveData<ArrayList<ExerciseDTO>>
      get() { return _exercises}
      set(value) {_exercises = value}

   var nutritionSearch:MutableLiveData<ArrayList<NutritionSearchResultDTO>>
      get() {return _nutritionSearch}
      set(value) {_nutritionSearch = value}

   var nutrition:MutableLiveData<ArrayList<Nutrition>>
      get() { return _nutrition}
      set(value) {_nutrition = value}

   var complex:MutableLiveData<ArrayList<NutritionSearchResultDTO>>
      get() { return _complex}
      set(value) {_complex = value}


   var foodSearchTerm = "pho"
   val complexSearchRepository = ComplexSearchRepository()
   val complexSearch: LiveData<ComplexSearchResult> get() = complexSearchRepository.getMutableLiveData(foodSearchTerm)
   override fun onCleared() {
      super.onCleared()
      complexSearchRepository.completableJob.cancel()
   }

/*   fun fetchComplexSearchResults(foodSearchTerm:String): List<ComplexSearch>? {
      return complexSearch.value
   }

 */

}

