package com.myfitnesstracker.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.firebase.ui.auth.AuthUI
import com.google.gson.GsonBuilder
import com.myfitnesstracker.BMIActivity
import com.myfitnesstracker.R
import com.myfitnesstracker.dto.ComplexSearchResult
import com.myfitnesstracker.service.GetItemDetailsDeserializer
import com.myfitnesstracker.ui.dto.BMI
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    private val AUTH_REQUEST_CODE = 2002



    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.bmis.observe(viewLifecycleOwner, Observer {
            bmis -> spnBmi.setAdapter(ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, bmis))
        })

        btnCalculate.setOnClickListener{
            saveWeightHeight()
        }

        btnTest.setOnClickListener {
            //txtTest.text = viewModel.fetchComplexSearchResults("pho").toString()
            var test = viewModel.fetchNutritionInfo("pho")

            var complex : ComplexSearchResult
            val gson = GsonBuilder()
                .registerTypeAdapter(ComplexSearchResult::class.java, GetItemDetailsDeserializer())
                .create()
            //complex = gson.fromJson(test, ComplexSearchResult::class.java)
            viewModel.nutrition
            Log.d("DTO", "Info: $test" + "title: dun dun dun")
            txtTest.text = viewModel.nutrition


        }
        btnSwitch.setOnClickListener {
            val intent = Intent()
            intent.setClass(activity!!, BMIActivity::class.java)
            activity!!.startActivity(intent)
        }
    }


    private fun saveWeightHeight() {
        var bmi = BMI().apply {
            weight = txtWeight.text.toString().toDouble()
            height = txtHeight.text.toString().toDouble()
            bmi = (weight / (height * height)) * 703
            txtBmi.text = bmi.toString()
        }
         viewModel.save(bmi)
    }

    fun saveBMI(bmi: String): String {
           var weight = txtWeight.text.toString().toDouble()
           var height = txtHeight.text.toString().toDouble()
           var bmi = (weight / (height * height)) * 703

            return "$bmi"
    }

    fun fetchUserInputBMI(weight: Double, height: Double): String {
        var weight = txtWeight.text.toString().toDouble()
        var height = txtHeight.text.toString().toDouble()

        return "$weight $height"
    }

    private fun logon() {
        var providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(), AUTH_REQUEST_CODE
        )
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
