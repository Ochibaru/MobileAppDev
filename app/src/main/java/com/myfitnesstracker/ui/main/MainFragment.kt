package com.myfitnesstracker.ui.main

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myfitnesstracker.R
import kotlinx.android.synthetic.main.main_fragment.*
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.lifecycle.Observer
import com.myfitnesstracker.ui.dto.BMI

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityBMI.setOnClickListener{
            activity?.let{
                val intent = Intent (it, MainViewModel::class.java)
                it.startActivity(intent)
            }
        }
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
    }

    private fun saveWeightHeight() {
        var bmi = BMI().apply {
            weight = txtWeight.text.toString().toDouble()
            height = txtHeight.text.toString().toDouble()
            bmi = (weight / (height * height)) * 703
            txtBmi.text = bmi.toString()
        }
        // viewModel.save(bmi)          Commenting out till error with firestore is fixed
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

    companion object {
        fun newInstance() = MainFragment()
    }
}
