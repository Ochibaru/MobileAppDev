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
        thenResultsContainCalculatedBMIImperial()
    }

    private fun givenAFeedOfUserInputIsAvailable() {
        mvm = MainViewModel()
    }

    private fun whenUserInputHeightWeightImperial() {
        mvm.fetchUserInputBMI(150.0, 65.0)     // not sure if this needs parameters
    }

    private fun thenResultsContainCalculatedBMIImperial() {
        var calculatedBMICorrectly = false
        var correctBMI = "24.96"
        var userBMI = mvm.sa
        //private BMIService bmiService
        //var calculatedBMI = userBMI

        if (correctBMI = userBMI) {
            calculatedBMICorrectly = true
        }
    }
/*
    @Test
    fun inputHeightWeightMetric_outputBMI() {
        givenAFeedOfUserInputIsAvailable()
        whenUserInputHeightWeightMetric()
        thenResultsContainCalculatedBMIMetric()
    }

    private fun whenUserInputHeightWeightMetric(){
        mvm.fetchUserInputBMI("165 cm", "68 kg")
    }

    private fun thenResultsContainCalculatedBMIMetric(){
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
        mvm.fetchUserInputBMI("afa", "rawr")
    }

    private fun thenThrowsError() {
        // needs to be done
    }
    */
}