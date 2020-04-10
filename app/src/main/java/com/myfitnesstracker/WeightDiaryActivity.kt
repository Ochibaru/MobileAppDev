package com.myfitnesstracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class WeightDiaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weight_diary_activity)

    }

    fun viewWeightDiaryActivity(view: View){
        val intent = Intent(this, WeightDiaryActivity::class.java)
        startActivity(intent)
    }
    fun viewTodaysOverviewActivity(view: View){
        val intent = Intent(this, TodaysOverviewActivity::class.java)
        startActivity(intent)
    }
    fun viewExerciseActivity(view: View){
        val intent = Intent(this, ExerciseActivity::class.java)
        startActivity(intent)
    }
    fun viewNutritionActivity(view: View){
        val intent = Intent(this, NutritionActivity::class.java)
        startActivity(intent)
    }
    fun viewBMIActivity(view: View){
        val intent = Intent(this, BMIActivity::class.java)
        startActivity(intent)
    }
}