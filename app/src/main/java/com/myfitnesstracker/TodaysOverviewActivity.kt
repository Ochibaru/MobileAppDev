package com.myfitnesstracker;

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.tabs.TabLayout
import com.gordonwong.materialsheetfab.DimOverlayFrameLayout
import com.gordonwong.materialsheetfab.MaterialSheetFab
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener
import com.myfitnesstracker.ui.main.fragments.MainPagerAdapter
import com.myfitnesstracker.ui.scripts.Fab
import com.myfitnesstracker.R


class TodaysOverviewActivity : AppCompatActivity(), View.OnClickListener  {

    private var drawerToggle: ActionBarDrawerToggle? = null
    private var drawerLayout: DrawerLayout? = null
    private var materialSheetFab: MaterialSheetFab<Fab>? = null
    private var statusBarColor = 0
    var coordinatorLayout: CoordinatorLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.today_overview_activity)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)

        setupActionBar()
        setupDrawer()
        setupFab()
        setupTabs()

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

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle!!.syncState()
    }

    override fun onBackPressed() {
        if (materialSheetFab!!.isSheetVisible) {
            materialSheetFab!!.hideSheet()
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Sets up the action bar.
     */
    private fun setupActionBar() {
        setSupportActionBar(findViewById<View>(R.id.toolbar) as androidx.appcompat.widget.Toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Sets up the navigation drawer.
     */
    private fun setupDrawer() {
        drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.opendrawer,
            R.string.closedrawer
        )
        drawerLayout!!.addDrawerListener(drawerToggle!!)
    }

    /**
     * Sets up the tabs.
     */
    private fun setupTabs() {
        // Setup view pager
        val viewpager = findViewById<View>(R.id.viewpager) as ViewPager
        viewpager.adapter = MainPagerAdapter(this, supportFragmentManager)
        viewpager.offscreenPageLimit = MainPagerAdapter.NUM_ITEMS
        updatePage(viewpager.currentItem)

        // Setup tab layout
        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(viewpager)
        viewpager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(i: Int) {
                updatePage(i)
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })
    }

    /**
     * Sets up the Floating action button.
     */
     fun setupFab() {

        val fabColor = ContextCompat.getColor(applicationContext, R.color.theme_accent)
        val sheetColor = ContextCompat.getColor(applicationContext, R.color.background_card)
        var fab: Fab = findViewById(R.id.fab)
        val sheetView: CardView = findViewById(R.id.fab_sheet)
        val overlay: DimOverlayFrameLayout = findViewById(R.id.overlay)

        // Create material sheet FAB
        materialSheetFab = MaterialSheetFab(fab, sheetView, overlay, sheetColor, fabColor)

        materialSheetFab!!.setEventListener(object : MaterialSheetFabEventListener() {
            override fun onShowSheet() {
                // Save current status bar color
                statusBarColor = getStatusBarColor()
                // Set darker status bar color to match the dim overlay
                setStatusBarColor(ContextCompat.getColor(applicationContext,R.color.theme_primary_dark2))
            }

            override fun onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor)
            }
        })

        // Set material sheet item click listeners
        val itemExercise: TextView = findViewById(R.id.fab_sheet_item_exercise)
        val itemFood: TextView = findViewById(R.id.fab_sheet_item_food)

        itemExercise.setOnClickListener(this)
        itemFood.setOnClickListener(this)
        }

    /**
     * Called when the selected page changes.
     *
     * @param selectedPage selected page
     */
    private fun updatePage(selectedPage: Int) {
        updateFab(selectedPage)
        updateSnackbar(selectedPage)
    }



    /**
     * Updates the FAB based on the selected page
     *
     * @param selectedPage selected page
     */
    private fun updateFab(selectedPage: Int) {
        when (selectedPage) {
            MainPagerAdapter.ALL_POS -> materialSheetFab?.showFab()
            MainPagerAdapter.SHARED_POS -> materialSheetFab?.showFab(
                0f,
                -resources.getDimensionPixelSize(R.dimen.snackbar_height).toFloat()
            )
            MainPagerAdapter.FAVORITES_POS -> materialSheetFab!!.hideSheetThenFab()
            else -> materialSheetFab?.hideSheetThenFab()
        }
    }

    /**
     * Updates the snackbar based on the selected page
     *
     * @param selectedPage selected page
     */
    private fun updateSnackbar(selectedPage: Int) {
        val snackbar = findViewById<View>(R.id.snackbar)
        when (selectedPage) {
            MainPagerAdapter.SHARED_POS -> snackbar.visibility = View.VISIBLE
            MainPagerAdapter.ALL_POS, MainPagerAdapter.FAVORITES_POS -> snackbar.visibility =
                View.GONE
            else -> snackbar.visibility = View.GONE
        }
    }


    /**
     * Toggles opening/closing the drawer.
     */
    private fun toggleDrawer() {
        if (drawerLayout!!.isDrawerVisible(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout!!.openDrawer(GravityCompat.START)
        }
    }


    override fun onClick(v: View?) {
        Toast.makeText(this, R.string.sheet_item_pressed, Toast.LENGTH_SHORT).show()
        materialSheetFab?.hideSheet()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.home -> {
                toggleDrawer()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getStatusBarColor(): Int {
        return window.statusBarColor
    }

    fun setStatusBarColor(color: Int) {
        window.statusBarColor = color
    }

}

