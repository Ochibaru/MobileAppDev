package com.myfitnesstracker.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.beust.klaxon.Klaxon
import com.beust.klaxon.PathMatcher
import com.myfitnesstracker.BMIActivity
import com.myfitnesstracker.R
import com.myfitnesstracker.dto.ComplexSearch
import com.myfitnesstracker.dto.ComplexSearchResult
import com.myfitnesstracker.ui.dto.BMI
import kotlinx.android.synthetic.main.main_fragment.*
import java.io.StringReader
import java.util.regex.Pattern

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
        /*
        activityBMI.setOnClickListener{
            activity?.let{
                val intent = Intent (it, MainViewModel::class.java)
                it.startActivity(intent)
            }
        }

         */
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

        fun getComplexResults(){
            viewModel!!.complexSearch.observe(this, Observer {
                val pathMatcher = object : PathMatcher {
                    override fun pathMatches(path: String)
                            = Pattern.matches(".*results.*.*title.*", path)
                    override fun onMatch(path: String, value: Any) {
                        txtTest.text = "$value"
                    }
                }
                Klaxon()
                    .pathMatcher(pathMatcher)
                    .parseJsonObject(it)
            })
        }


        viewModel.complexSearch.observe(viewLifecycleOwner, Observer {
            complexSearch -> spnTest.setAdapter(ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, complexSearch))
        })
        btnTest.setOnClickListener {
            //txtTest.text = viewModel.fetchComplexSearchResults("pho").toString()

            var testCall = viewModel.fetchComplexSearchResults("pho").toString()
            //jsonTestParse()
            val result = Klaxon().parse<ComplexSearchResult>(testCall)
            txtTest.text = result?.title
        }
        btnSwitch.setOnClickListener {
            val intent = Intent()
            intent.setClass(activity!!, BMIActivity::class.java)
            activity!!.startActivity(intent)
        }
    }

    private fun testTesting(test:String){

        var test = viewModel.fetchComplexSearchResults("pho")
    }

    fun jsonTestParse(){
        val jsonTest: String = "{\"results\":[{\"id\":275468,\"title\":\"Pho\",\"image\":\"https://spoonacular.com/recipeImages/275468-312x231.jpg\",\"imageType\":\"jpg\"}],\"offset\":0,\"number\":1,\"totalResults\":96}"

        val klaxon = Klaxon()
        val parsed = klaxon.parseJsonObject(StringReader(jsonTest))
        val dataArray = parsed.array<Any>("results")
        val results = dataArray?.let { klaxon.parseFromJsonArray<ComplexSearchResult>(it) }
        println(results)
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
