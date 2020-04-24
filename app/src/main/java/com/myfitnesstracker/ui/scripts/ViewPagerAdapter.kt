package com.myfitnesstracker.ui.scripts

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.myfitnesstracker.ui.main.BMIFragment
import com.myfitnesstracker.ui.main.TodaysOverviewFragment

/*
*   Page adapter allows the switching between fragments in the same activity
*/

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            BMI_POS -> BMIFragment.newInstance()
            else -> TodaysOverviewFragment.newInstance()
        }
    }

    override fun getItemCount(): Int {
        return CARD_ITEM_SIZE
    }

    companion object {
        private const val CARD_ITEM_SIZE = 2
        const val BMI_POS = 0
        // To add more fragments for tab, need to add var here, eg val NEW_POS = 1, change the CARD_ITEM_SIZE size and add the NEW_POS to createFragment fun
    }
}