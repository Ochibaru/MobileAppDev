package com.myfitnesstracker.ui.main

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.myfitnesstracker.R


class TodaysOverviewFragment : Fragment()  {

    private lateinit var viewModel: MainViewModel
    lateinit var lblBurned: TextView
    lateinit var lblEaten: TextView
    lateinit var lblCarbs: TextView
    lateinit var lblFats: TextView
    lateinit var lblProtein: TextView
    lateinit var pieFoodChart: PieChart
    var entriesNutrition = ArrayList<PieEntry>()
    val listColors = ArrayList<Int>()
    val pieDataSet: PieDataSet = PieDataSet(entriesNutrition, "")
    var pieData: PieData = PieData()
    val SERIF_BOLD: Typeface = Typeface.create("sans-serif", Typeface.BOLD)
    val TEXT_SIZE = 12F

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.today_overview_activity, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        // Create pie chart
        pieFoodChart = view.findViewById(R.id.pieFood)
        pieFoodChart.description.isEnabled = false
        pieFoodChart.setHoleColor(obtainColor(requireContext(), R.color.transparent, ContextCompat::getColor))
        entriesNutrition.add(PieEntry(1F, "Carbs")) //  (obtainColor(requireContext(), R.color.text_white_87, ContextCompat::getColor))
        listColors.add(obtainColor(requireContext(), R.color.blueish, ContextCompat::getColor))
        entriesNutrition.add(PieEntry(1F, "Fats"))
        listColors.add(obtainColor(requireContext(), R.color.tealish, ContextCompat::getColor))
        entriesNutrition.add(PieEntry(1F, "Protein"))
        listColors.add(obtainColor(requireContext(), R.color.greenish, ContextCompat::getColor))
        pieDataSet.colors = listColors
        pieData = PieData(pieDataSet)
        pieData.setValueTypeface(SERIF_BOLD)
        pieData.setValueTextColor(obtainColor(requireContext(), R.color.text_white_87, ContextCompat::getColor))
        pieData.setValueTextSize(14F)
        pieFoodChart.data = pieData

        lblBurned = view.findViewById(R.id.lblBurned)
        lblEaten = view.findViewById(R.id.lblEaten)
        lblCarbs = view.findViewById(R.id.lblCarbs)
        lblFats = view.findViewById(R.id.lblFats)
        lblProtein = view.findViewById(R.id.lblProtein)



        viewModel.infoExercise.observe(viewLifecycleOwner, Observer {
                infoExercise ->
            lblBurned.text = getString(R.string.format_kcal, infoExercise.nfCalories!!.toString().substringBefore("."))
        })

        viewModel.infoNutri.observe(viewLifecycleOwner, Observer {
                infoNutri ->
            // Update the pie chart on every new food entry
            val entriesUpdate = ArrayList<PieEntry>()
            val carbPercentage = infoNutri.carbs!!.toFloat() / (infoNutri.carbs!!.toFloat() + infoNutri.protein!!.toFloat() + infoNutri.fat!!.toFloat())
            val fatPercentage = infoNutri.fat!!.toFloat() / (infoNutri.carbs!!.toFloat() + infoNutri.protein!!.toFloat() + infoNutri.fat!!.toFloat())
            val proteinPercentage = infoNutri.protein!!.toFloat() / (infoNutri.carbs!!.toFloat() + infoNutri.protein!!.toFloat() + infoNutri.fat!!.toFloat())
            Log.d("Percentage", carbPercentage.toString())
            entriesUpdate.add(PieEntry(carbPercentage, "Carbs"))
            entriesUpdate.add(PieEntry(proteinPercentage, "Protein"))
            entriesUpdate.add(PieEntry(fatPercentage, "Fat"))
            val pieDataSetUpdate = PieDataSet(entriesUpdate, "")
            val legendPie = pieFoodChart.legend
            pieDataSetUpdate.colors = listColors
            pieData = PieData(pieDataSetUpdate)
            pieData.setValueFormatter(PercentFormatter(pieFoodChart))
            pieFoodChart.setUsePercentValues(true)
            pieFoodChart.setEntryLabelColor(obtainColor(requireContext(), R.color.grayish, ContextCompat::getColor))
            pieFoodChart.setEntryLabelTypeface(SERIF_BOLD)
            pieData.setValueTypeface(SERIF_BOLD)
            pieData.setValueTextColor(obtainColor(requireContext(), R.color.grayish, ContextCompat::getColor))
            pieData.setValueTextSize(TEXT_SIZE)
            legendPie.textColor = (obtainColor(requireContext(), R.color.grayish, ContextCompat::getColor))
            legendPie.textSize = TEXT_SIZE
            pieFoodChart.data = pieData
            pieFoodChart.invalidate()

            // Sets labels in layout with live data
            lblEaten.text = getString(R.string.format_kcal, infoNutri.calories)
            lblCarbs.text = getString(R.string.format_grams, infoNutri.carbs)
            lblFats.text = getString(R.string.format_grams, infoNutri.fat)
            lblProtein.text = getString(R.string.format_grams, infoNutri.protein)
        })

        return view
    }


    companion object {
        fun newInstance() = TodaysOverviewFragment()
    }
}

fun obtainColor(context: Context, colorRes: Int, colorLookup: (Context, Int) -> Int) = colorLookup(context, colorRes)

