package com.myfitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.gordonwong.materialsheetfab.DimOverlayFrameLayout
import com.gordonwong.materialsheetfab.MaterialSheetFab
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener
import com.myfitnesstracker.dto.ExerciseRequest
import com.myfitnesstracker.ui.main.MainViewModel
import com.myfitnesstracker.ui.scripts.Fab
import com.myfitnesstracker.ui.scripts.ViewPagerAdapter
import com.myfitnesstracker.ui.scripts.onClickDebounced

open class  MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout
    private lateinit var userEmail: String
    private lateinit var viewModel: MainViewModel
    private var materialSheetFab: MaterialSheetFab<Fab>? = null
    private var statusBarColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_fragment)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        checkCurrentUser()
        setupFab()

        // Wires button to search for user's food query
        R.id.fab_button_item_food.onClickDebounced {
            MaterialDialog(this).show {
                input(hint = "Enter food to search") { _, text ->
                    val inputText = text.toString()
                    viewModel.fetchNutritionInfo(inputText)
                    Log.d("Food Input",  "$text")
                }
                positiveButton(R.string.search)
            }
        }
        // Wires button to search for user's exercise query
        R.id.fab_button_item_exercise.onClickDebounced {
            MaterialDialog(this).show {
                input(hint = "Enter exercise") { _, text ->
                    val inputText = text.toString()
                    val exRequest = ExerciseRequest().apply {
                        query = inputText
                    }
                    viewModel.fetchExerciseInfo(exRequest)
                    Log.d("Enter Exercise",  "$text")
                }
                positiveButton(R.string.search)
            }
        }

        // Create scrollable tabs
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = createCardAdapter()
        tabLayout = findViewById(R.id.tabs)
        viewPager.setAdapter(createCardAdapter())
        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                if (position == 1){
                    tab.text = "Today's Overview"
                } else {
                    tab.text = "BMI Calculator"
                }
            }).attach()

    }

    override fun onBackPressed() {
        if (materialSheetFab!!.isSheetVisible) {
            materialSheetFab!!.hideSheet()
        } else {
            super.onBackPressed()
        }
    }

    /*
     * Sets up FAB (Floating action bar).
     */
    fun setupFab() {

        val fabColor = ContextCompat.getColor(applicationContext, R.color.theme_accent)
        val sheetColor = ContextCompat.getColor(applicationContext, R.color.background_card)
        val fab: Fab = findViewById(R.id.fab)
        val sheetView: CardView = findViewById(R.id.fab_sheet)
        val overlay: DimOverlayFrameLayout = findViewById(R.id.overlay)

        // Create FAB list
        materialSheetFab = MaterialSheetFab(fab, sheetView, overlay, sheetColor, fabColor)

        materialSheetFab!!.setEventListener(object : MaterialSheetFabEventListener() {
            override fun onShowSheet() {
                // Save current color
                statusBarColor = getStatusBarColor()
                // Set darker color to match the dim overlay
                setStatusBarColor(ContextCompat.getColor(applicationContext,R.color.tealish))
            }
            override fun onHideSheet() {
                // Revert color
                setStatusBarColor(statusBarColor)
            }
        })

        // Set material sheet item click listeners for onClick
        val itemExercise: Button = findViewById(R.id.fab_button_item_exercise)
        val itemFood: Button = findViewById(R.id.fab_button_item_food)

        itemExercise.setOnClickListener(this)
        itemFood.setOnClickListener(this)
    }

    // This is temporary, only using to make sure button is receiving click
    override fun onClick(v: View?) {
        Toast.makeText(this, R.string.sheet_item_pressed, Toast.LENGTH_SHORT).show()
        materialSheetFab?.hideSheet()
    }


    fun getStatusBarColor(): Int {
        return window.statusBarColor
    }

    fun setStatusBarColor(color: Int) {
        window.statusBarColor = color
    }

    // Calls debounce function
    private fun Int.onClickDebounced(click: () -> Unit) {
        findViewById<Button>(this).onClickDebounced { click() }
    }

    private fun createCardAdapter(): ViewPagerAdapter? {
        return ViewPagerAdapter(this)
    }

    // Checks to make sure user is signed in so the firebase database is saved correctly
    private fun checkCurrentUser() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            userEmail = viewModel.getUserProfile()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


}
