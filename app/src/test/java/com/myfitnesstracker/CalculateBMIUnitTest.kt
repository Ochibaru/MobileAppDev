package com.myfitnesstracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myfitnesstracker.ui.main.BMIFragment
import com.myfitnesstracker.ui.main.MainViewModel
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CalculateBMIUnitTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm: MainViewModel
    lateinit var mvf: BMIFragment

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
        mvm.fetchHeightWeight("150.0","65.0")  // not sure if this needs parameters
    }

    private fun thenResultsContainCalculatedBMIImperial() {
        var calculatedBMICorrectly = false
        //var correctBMI = "24.96"
        //var redbudFound = false;
        mvm.bmis.observeForever {
            // /here is where we do the observing
            assertNotNull(it)
            assertTrue(it.size > 0)
            it.forEach {
                if (it.weight == 150.0 && it.height == 65.0 && it.bmi == 24.96) {
                    calculatedBMICorrectly = true
                }
            }
            assertTrue(calculatedBMICorrectly)
        }
        /*var userBMI = mvm.saveBMI(correctBMI)
        //private BMIService bmiService
        //var calculatedBMI = userBMI

        if (correctBMI == userBMI) {
            calculatedBMICorrectly = true
        }*/
    }
}