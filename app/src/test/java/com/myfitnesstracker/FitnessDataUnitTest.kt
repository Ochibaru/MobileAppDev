package com.myfitnesstracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myfitnesstracker.ui.main.MainViewModel
import org.junit.Assert.assertNotNull

import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class FitnessDataUnitTest {


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm: MainViewModel
/*

    @Test
    fun confirmRun_outputsRun() {
        givenAFeedOfFitnessDataAreAvailable()
        whenSearchForRun()
        thenResultsContainRunExerciseInfo()
    }


    private fun givenAFeedOfFitnessDataAreAvailable(){
        mvm = MainViewModel()
    }


    private fun whenSearchForRun(){
        mvm.fetchExerciseInfo("run")
    }


    private fun thenResultsContainRunExerciseInfo() {
        var runFound = false;
        mvm.exercise.observeForever {
            // here is the observing
            assertNotNull(it)
            assertTrue(it.size > 0)
            it.forEach {
                if (it.time == "20 minutes" && it.calories == "123")
                    runFound = true
            }
        }
        assertTrue(runFound)
    }
*/
}