package com.myfitnesstracker.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.myfitnesstracker.ui.dto.BMI

class MainViewModel : ViewModel() {

   private var mainFragment: MainFragment = MainFragment()
   private lateinit var firestore : FirebaseFirestore
   private var _bmis: MutableLiveData<ArrayList<BMI>> = MutableLiveData<ArrayList<BMI>>()

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
         .addOnSuccessListener {
            Log.d("Firebase", "Calculation Succeeded")
         }
         .addOnFailureListener{
            Log.d("Firebase","Calculate Failed")
         }
   }

   internal var bmis:MutableLiveData<ArrayList<BMI>>
      get() {return _bmis}
      set(value) {_bmis = value}

/*
    //var userBMI = mainFragment.calculateBMI()
    fun fetchUserBMI(bmi: Double){

    }
*/
    fun fetchUserInputBMI(weight: Double, height: Double) {
      var userInputBMI = mainFragment.fetchUserInputBMI(weight, height)
   }
}

