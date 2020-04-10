package com.myfitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.myfitnesstracker.ui.main.MainFragment
import com.myfitnesstracker.ui.main.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*

open class MainActivity : AppCompatActivity() {

    //private EditText feet;
    var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }


        //feet = (EditText)findViewById(R.id.txtFeet);

        //activityBMI = findViewById<Button>(R.id.activityBMI)
        //activityBMI.setOnClickListener(new View.OnClickListener(){

        //})

    }





}

/*
    Button button = (Button) rootView.findViewById(R.id.button1);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), AnotherActivity.class);
            startActivity(intent);
        }
    });
    val activityBMI = viewModel.findViewById(R.id.activityBMI) as Button
 */