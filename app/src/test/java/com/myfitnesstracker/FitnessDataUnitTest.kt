package com.myfitnesstracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myfitnesstracker.ui.main.MainViewModel
import com.myfitnesstracker.ui.scripts.StringManipulator
import org.junit.Assert.*

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class FitnessDataUnitTest {

    val stringManipulator = StringManipulator()
    val badVitaminSpaceString = "Vitamin B9"


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm: MainViewModel


    @Test
    fun confirmRun_outputsRun() {
        givenAFeedOfFitnessDataAreAvailable()
        whenSearchForRun()
        parseThroughData()
        thenResultsContainRunExerciseInfo()
    }

    @Test
    fun parseThroughData() {
        val replaceVitamin = stringManipulator.removeWhitespaceVitamin(badVitaminSpaceString)
        assertEquals("VitaminB9", replaceVitamin)
    }


    private fun givenAFeedOfFitnessDataAreAvailable() {
        mvm = MainViewModel()
    }


    private fun whenSearchForRun() {
        //mvm.fetchExerciseInfo("run")
    }

    private fun thenResultsContainRunExerciseInfo() {
        /*
                var runFound = false;
        mvm.exercises.observeForever { it ->
        // here is the observing
        assertNotNull(it)
        assertTrue(it.size > 0)
        it.forEach {
            if (it.duration_min == "20" && it.nf_calories == "229")
                runFound = true
        }
    }
    assertTrue(runFound)

         */
    }
}
