package com.myfitnesstracker.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.myfitnesstracker.R
import com.myfitnesstracker.ui.dto.BMI
import kotlinx.android.synthetic.main.bmi_activity.*
import kotlinx.android.synthetic.main.bmi_activity.view.*

class BMIFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    var genderInput = ""
    var heightCheck = ""
    var weightCheck = ""
    var weightInput = 0.0
    var heightInput = 0.0
    var bmiCalculation = 0.0
    var measurementSystem = ""
    var ageInput = 0
    var goalInput = 0.0
    lateinit var imagebuttonMale : ImageButton
    val WHITE_COLOR = "#FFFFFF"
    val OTHER_COLOR = "#72909D"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.bmi_activity, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        imagebuttonMale = view.findViewById(R.id.btnMale)

        view.btnMale.setOnClickListener{
            genderInput = "male"
            btnMale.setImageResource(R.drawable.ic_btnmaleselected_bmi)
            btnFemale.setImageResource(R.drawable.btnfemale_bmi)
        }
        view.btnFemale.setOnClickListener{
            genderInput = "female"
            btnFemale.setImageResource(R.drawable.ic_btnfemaleselected_bmi)
            btnMale.setImageResource(R.drawable.btnmale_bmi)
        }
        view.btnHeightCM.setOnClickListener {
            heightCheck = "cm"
            weightCheck = "kg"
            btnHeightCM.setTextColor(Color.parseColor(WHITE_COLOR))
            btnHeightFT.setTextColor(Color.parseColor(OTHER_COLOR))
        }
        view.btnHeightFT.setOnClickListener {
            heightCheck = "ft"
            weightCheck = "lb"
            btnHeightFT.setTextColor(Color.parseColor(WHITE_COLOR))
            btnHeightCM.setTextColor(Color.parseColor(OTHER_COLOR))
        }
        view.btnWeightKG.setOnClickListener {
            weightCheck = "kg"
            heightCheck = "cm"
            btnWeightKG.setTextColor(Color.parseColor(WHITE_COLOR))
            btnWeightLB.setTextColor(Color.parseColor(OTHER_COLOR))
        }
        view.btnWeightLB.setOnClickListener {
            weightCheck = "lb"
            heightCheck = "ft"
            btnWeightLB.setTextColor(Color.parseColor(WHITE_COLOR))
            btnWeightKG.setTextColor(Color.parseColor(OTHER_COLOR))
        }
        view.btnCalculateBMI.setOnClickListener {
            calculateBMI()
        }

        return view
    }

    private fun calculateBMI(){
        if (txtHeight.text.toString() == "" || txtWeight.text.toString() == ""){
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage("Please make sure measurements are not empty.")
                .setCancelable(false)
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, _ -> dialog.cancel()
                })
            val alert = dialogBuilder.create()
            alert.setTitle("Empty Measurement")
            alert.show()
        } else {
            weightInput = txtWeight.text.toString().toDouble()
            heightInput = txtHeight.text.toString().toDouble()

            ageInput = txtAge.text.toString().toInt()
            goalInput = txtGoal.text.toString().toDouble()

            if (heightCheck == "ft" && weightCheck == "lb"){
                bmiCalculation = (weightInput / (heightInput * heightInput)) * 703
                measurementSystem = "imperial"
            }
            else if (heightCheck == "cm" && weightCheck == "kg"){
                bmiCalculation = (weightInput / (heightInput * heightInput))
                measurementSystem = "metric"
            }

            Toast.makeText(context, "BMI: $bmiCalculation", Toast.LENGTH_SHORT).show();


            val bmi = BMI().apply {
                measurement = measurementSystem
                height = heightInput
                weight = weightInput
                bmi = bmiCalculation
                age = ageInput
                goal = goalInput
                gender = genderInput
            }
            viewModel.save(bmi)
        }
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
    companion object {
        fun newInstance() = BMIFragment()
    }
}