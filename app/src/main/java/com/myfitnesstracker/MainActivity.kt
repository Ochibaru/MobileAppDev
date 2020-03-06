package com.myfitnesstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.myfitnesstracker.ui.main.MainFragment
import kotlinx.android.synthetic.main.main_fragment.*

class MainActivity : AppCompatActivity() {

    //private EditText feet;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        //feet = (EditText)findViewById(R.id.txtFeet);

    }



}
