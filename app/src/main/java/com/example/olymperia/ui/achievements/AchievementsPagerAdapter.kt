package com.example.olymperia.ui.achievements

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.olymperia.ui.achievements.MapAchievementsFragment

class AchievementsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MedalsFragment()
            1 -> TrophiesFragment()
            2 -> HonorsFragment()
            3 -> MapAchievementsFragment()
            else -> Fragment() // fallback vacío
        }
    }
}
