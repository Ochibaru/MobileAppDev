package com.myfitnesstracker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.bmi_activity.*

class BMIActivity : AppCompatActivity() {

    var gender = ""
    var heightCheck = ""
    var weightCheck = ""
    var weight = 0.0
    var height = 0.0
    var bmi = 0.0
    lateinit var imagebutton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bmi_activity)

        btnMale.setOnClickListener{
            gender = "male"
            imagebutton.setBackgroundResource(R.drawable.btnmaleselected_bmi)
            btnFemale.setBackgroundResource(R.drawable.btnfemale_bmi)
        }
        btnFemale.setOnClickListener{
            gender = "female"
            imagebutton.setBackgroundResource(R.drawable.btnfemaleselected_bmi)
            btnMale.setBackgroundResource(R.drawable.btnmale_bmi)
        }
        btnHeightCM.setOnClickListener {
            heightCheck = "cm"
            weightCheck = "kg"
            btnHeightCM.setTextColor(Color.parseColor("#FFFFFF"))
            btnHeightFT.setTextColor(Color.parseColor("#72909D"))
        }
        btnHeightFT.setOnClickListener {
            heightCheck = "ft"
            weightCheck = "lb"
            btnHeightFT.setTextColor(Color.parseColor("#FFFFFF"))
            btnHeightCM.setTextColor(Color.parseColor("#72909D"))
        }
        btnWeightKG.setOnClickListener {
            weightCheck = "kg"
            heightCheck = "cm"
            btnWeightKG.setTextColor(Color.parseColor("#FFFFFF"))
            btnWeightLB.setTextColor(Color.parseColor("#72909D"))
        }
        btnWeightLB.setOnClickListener {
            weightCheck = "lb"
            heightCheck = "ft"
            btnWeightLB.setTextColor(Color.parseColor("#FFFFFF"))
            btnWeightKG.setTextColor(Color.parseColor("#72909D"))
        }
        btnCalculateBMI.setOnClickListener {
            calculateBMI()
        }
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

    private fun calculateBMI(){
        weight = txtWeight.text.toString().toDouble()
        height = txtHeight.text.toString().toDouble()

        if (heightCheck == "ft" && weightCheck == "lb"){
            bmi = (weight / (height * height)) * 703
        }
        else if (heightCheck == "cm" && weightCheck == "kg"){
            bmi = (weight / (height * height))
        }
        /* else {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage("Please make sure measurements are both imperial or both metric.")
                .setCancelable(false)
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })
            val alert = dialogBuilder.create()
            alert.setTitle("Measurement Mismatch")
            alert.show()
        }
         */
        Toast.makeText(applicationContext, "BMI: $bmi",Toast.LENGTH_SHORT).show();
    }

}
