package com.myfitnesstracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myfitnesstracker.ui.main.MainViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CalculateBMIUnitTest {


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm: MainViewModel


    @Test
    fun inputHeightWeightImperial_outputBMI() {
        givenAFeedOfUserInputIsAvailable()
        whenUserInputHeightWeightImperial()
        thenResultsContainCalculatedBMI()
    }


    private fun givenAFeedOfUserInputIsAvailable() {
        mvn = MainViewModel()
    }


    private fun whenUserInputHeightWeightImperial() {
        mvn.fetchUserInputBMI("65 in", "150 lbs")     // not sure if this needs parameters
    }


    private fun thenResultsContainCalculatedBMI() {
        var calculatedBMICorrectly = false
        val correctBMI = 24.96
        private IBMIService bmiService
        var calculatedBMI = bmiService.calculateBMI("65 in", "150 lbs")


        if (correctBMI = calculatedBMI)
            calculatedBMICorrectly = true


    }


    @Test
    fun inputHeightWeightMetric_outputBMI() {
        givenAFeedOfUserInputIsAvailable()
        whenUserInputHeightWeightMetric()
        thenResultsContainCalculatedBMI()
    }


    private fun whenUserInputHeightWeightMetric(){
        mvn.fetchUserInputBMI("165 cm", "68 kg")
    }


    private fun thenResultsContainCalculatedBMI(){
        var calculatedBMICorrectly = false
        val correctBMI = 24.98
        private IBMIService bmiService
        var calculatedBMI = bmiService.calculateBMI("165 cm", "68 kg")


        if (correctBMI = calculatedBMI)
            calculatedBMICorrectly = true
    }


    @Test
    fun inputHeightWeightNaN() { //NaN = not a number
        givenAFeedOfUserInputIsAvailable()
        whenUserInputsNaN()
        thenThrowsError()
    }


    private fun whenUserInputsNaN() {
        mvn.fetchUserInputBMI("afa", "rawr")


    }


    private fun thenThrowsError() {
        // needs to be done
    }


}