package com.myfitnesstracker.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.*
import com.myfitnesstracker.dto.*
import com.myfitnesstracker.service.ExerciseService
import com.myfitnesstracker.service.NutritionService
import com.myfitnesstracker.ui.dto.BMI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainViewModel : ViewModel() {

   private var _bmis: MutableLiveData<ArrayList<BMI>> = MutableLiveData<ArrayList<BMI>>()
   private var _infoNutri:MutableLiveData<Nutrition> = MutableLiveData<Nutrition>()
   private var _infoExercise:MutableLiveData<Exercise> = MutableLiveData<Exercise>()
   private var _exercises: Exercise = Exercise()
   private var _nutrition: ComplexSearchResult = ComplexSearchResult()
   var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
   var nutritionService: NutritionService = NutritionService
   var exerciseService: ExerciseService = ExerciseService

   val current = LocalDateTime.now()
   val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
   val formatted = current.format(formatter)
   // Replace where var formatted is to have test data input, use formatted to get current dates database information
   var fixedFormat: String = "2020-04-20"

   val LISTEN_FAIL = "Listen Failed"

   init {
      firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
      listenToBMI()
      listenToNutrition()
      listenToExercise()
   }

   private fun listenToBMI() {
      firestore.collection("BMI").addSnapshotListener{
         snapshot, e ->
         if (e != null){
            Log.w(TAG, LISTEN_FAIL, e)
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

   private fun listenToNutrition(){
      firestore.collection("Food").document("Date").collection(formatted).addSnapshotListener{
            snapshot, e ->
         if (e != null){
            Log.w(TAG, LISTEN_FAIL, e)
            return@addSnapshotListener
         }
         if (snapshot != null){
            var nutri = Nutrition()
            nutri.calories = "0"
            nutri.carbs = "0"
            nutri.fat = "0"
            nutri.protein = "0"
            val documents = snapshot.documents
            documents.forEach{
               val nutrition = it.toObject(Nutrition::class.java)
               if (nutrition != null){
                  val addCalories = nutrition.calories!!.toInt() + nutri.calories!!.toInt()
                  val addFats = removeCharacter(nutrition.fat!!, "g", "").toInt() + nutri.fat!!.toInt()
                  val addCarbs = removeCharacter(nutrition.carbs!!, "g", "").toInt() + nutri.carbs!!.toInt()
                  val addProteins = removeCharacter(nutrition.protein!!, "g", "").toInt() + nutri.protein!!.toInt()

                  nutri.calories = addCalories.toString()
                  nutri.fat = addFats.toString()
                  nutri.carbs = addCarbs.toString()
                  nutri.protein = addProteins.toString()
               }
            }
            _infoNutri.value = nutri
         }
      }
   }

   private fun listenToExercise(){
      firestore.collection("Exercise").document("Date").collection(formatted).addSnapshotListener{
            snapshot, e ->
         if (e != null){
            Log.w(TAG, LISTEN_FAIL, e)
            return@addSnapshotListener
         }
         if (snapshot != null){
            val exerc = Exercise()
            exerc.met = 0.0
            exerc.durationMin = 0.0
            exerc.name = ""
            exerc.nfCalories = 0.0
            exerc.userInput = ""
            val documents = snapshot.documents

            documents.forEach{
               val exercise = it.toObject(Exercise::class.java)
               if (exercise != null){
                  exerc.nfCalories = exercise.nfCalories
               }
            }
            _infoExercise.value = exerc
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

   internal var bmis:MutableLiveData<ArrayList<BMI>>
      get() {return _bmis}
      set(value) {_bmis = value}

   internal var infoNutri:MutableLiveData<Nutrition>
      get() {return _infoNutri}
      set(value) {_infoNutri = value}

   internal var infoExercise:MutableLiveData<Exercise>
      get() {return _infoExercise}
      set(value) {_infoExercise = value}

   fun fetchExerciseInfo(exerciseName: ExerciseRequest) {
      exerciseService.fetchExercise(exerciseName)
   }


   fun fetchNutritionInfo(nutritionID:String) {
      nutritionService.doComplexSearch(nutritionID)
   }

   var exercises:Exercise
      get() { return _exercises}
      set(value) {_exercises = value}

   var nutrition:ComplexSearchResult
      get() { return _nutrition}
      set(value) {_nutrition = value}

   // Remove value from string to be able to convert string to a number
   fun removeCharacter(enitre:String, target:String, replacement:String):String{
      val entireString: String = enitre
      val targetString: String = target
      val replacementString: String = replacement
      return entireString.replace(targetString, replacementString)
   }

}

