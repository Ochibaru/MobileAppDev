package com.myfitnesstracker

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.myfitnesstracker.ui.secondary.SecondaryFragment

open class SecondaryActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondary_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_secondary, SecondaryFragment.newInstance())
                .commitNow()
        }
    }

    open fun changeLayoutWeightDiary(view: View?) {
        // Do something in response to button click
    }

    open fun changeLayoutTodaysOverview(view: View?) {
        // Do something in response to button click
    }

    open fun changeLayoutAddExercise(view: View?) {
        // Do something in response to button click
    }

    open fun changeLayoutAddNutrition(view: View?) {
        // Do something in response to button click
    }
}