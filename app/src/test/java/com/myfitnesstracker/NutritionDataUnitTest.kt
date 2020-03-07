package com.myfitnesstracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.myfitnesstracker.ui.main.MainViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class NutritionDataUnitTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm: MainViewModel

// alt + enter to auto create function



    @Test
    fun confirmPho_outputsPho() {
        givenAFeedOfNutritionDataAreAvailable()
        whenSearchForPho()
        thenResultContainsPhoNutritionInfo()
    }


    private fun givenAFeedOfNutritionDataAreAvailable() {
        mvm = MainViewModel()
    }


    private fun whenSearchForPho() {
        mvm.fetchNutritionInfo("pho")
    }


    private fun thenResultContainsPhoNutritionInfo() {
        var phoFound = false;
        mvm.Nutrition.observeForever {
            // here is the observing

            assertNotNull(it)
            assertTrue(it.size > 0)
            it.forEach {
                if (it.calories == "268" && it.carbs == "41g" && it.protein == "16g" && it.fat == "3g")
                    phoFound = true
            }
        }
        assertTrue(phoFound)
    }


    @Test
    fun searchForGarbage_returnsNothing() {
        givenAFeedOfNutritionDataAreAvailable()
        whenISearchForGarbage()
        thenIGetZeroResults()
    }


    private fun whenISearchForGarbage() {
        mvm.fetchNutritionInfo("asfggagsdfjaiagg")
    }


    private fun thenIGetZeroResults() {
        mvm.nutrition.observeForever {
            assertEquals( 0, it.size)
        }
    }


}

private fun Any.observeForever(n: Any) {

}
