package com.myfitnesstracker.ui.secondary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.myfitnesstracker.R
import kotlinx.android.synthetic.main.secondary_activity.*

class SecondaryFragment : Fragment() {

    private lateinit var viewModel: SecondaryViewModel

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

        override fun onCreateView(
        inflater: LayoutInflater, container_secondary: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bmi_fragment, container_secondary, false)
    }

    companion object {
        fun newInstance() = SecondaryFragment()
    }
}