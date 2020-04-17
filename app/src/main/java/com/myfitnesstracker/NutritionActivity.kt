package com.myfitnesstracker;

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat



open class NutritionActivity : AppCompatActivity() {

        private var iv: ImageView? = null
        private var text: TextView? = null
        private var searchToBar: AnimatedVectorDrawable? = null
        private var barToSearch: AnimatedVectorDrawable? = null
        private var offset = 0f
        private var interp: Interpolator? = null
        private var duration = 0
        private var expanded = false

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nutrition_activity)

                iv = findViewById(R.id.search)
                text = findViewById(R.id.text)
                searchToBar = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.anim_search_to_bar,
                        null
                ) as AnimatedVectorDrawable?
                barToSearch = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.anim_bar_to_search,
                        null
                ) as AnimatedVectorDrawable?
                interp = AnimationUtils.loadInterpolator(
                        this,
                        android.R.interpolator.linear_out_slow_in
                )
                duration = resources.getInteger(R.integer.duration_bar)
                // iv is sized to hold the search+bar so when only showing the search icon, translate the
                // whole view left by half the difference to keep it centered
                // iv is sized to hold the search+bar so when only showing the search icon, translate the
                // whole view left by half the difference to keep it centered
                offset = -71f * resources.displayMetrics.scaledDensity.toInt()
                iv?.let { iv!!.translationX = offset }
                starter()

        }

        fun viewWeightDiaryActivity(view: View){
                val intent = Intent(this, WeightDiaryActivity::class.java)
                startActivity(intent)
        }
        fun viewTodaysOverviewActivity(view: View){
                val intent = Intent(this, TodaysOverviewActivity::class.java)
                startActivity(intent)
        }
        fun viewExerciseActivity(view: View){
                val intent = Intent(this, ExerciseActivity::class.java)
                startActivity(intent)
        }
        fun viewNutritionActivity(view: View){
                val intent = Intent(this, NutritionActivity::class.java)
                startActivity(intent)
        }
        fun viewBMIActivity(view: View){
                val intent = Intent(this, BMIActivity::class.java)
                startActivity(intent)
        }

         open fun starter() {
                iv!!.setImageDrawable(barToSearch)
                barToSearch!!.start()
                iv!!.animate().translationX(offset).setDuration(duration.toLong()).interpolator =
                        interp
                text!!.alpha = 0f
        }

        open fun animate(view: View?) {
                if (!expanded) {
                        iv!!.setImageDrawable(searchToBar)
                        searchToBar!!.start()
                        iv!!.animate().translationX(0f).setDuration(duration.toLong()).interpolator =
                                interp
                        text!!.animate().alpha(1f).setStartDelay(duration - 100.toLong())
                                .setDuration(100).interpolator = interp
                } else {
                        iv!!.setImageDrawable(barToSearch)
                        barToSearch!!.start()
                        iv!!.animate().translationX(offset)
                                .setDuration(duration.toLong()).interpolator =
                                interp
                        text!!.alpha = 0f
                }
                expanded = !expanded
        }

}
