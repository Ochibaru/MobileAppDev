package com.myfitnesstracker

class FitnessDataUnitTest {


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm:MainViewModel


    @Test
    fun confirmRun_outputsRun() {
        givenAFeedOfFitnessDataAreAvailable()
        whenSearchForRun()
        thenResultsContainRunExerciseInfo()
    }


    private fun givenAFeedOfFitnessDataAreAvailable(){
        mvn = MainViewModel()
    }


    private fun whenSearchForRun(){
        mvn.fetchExerciseInfo("run")
    }


    private fun thenResultsContainRunExerciseInfo() {
        var runFound = false;
        mvn.exercise.observeForever {
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

}