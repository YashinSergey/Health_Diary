package com.healthdiary.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.healthdiary.R
import com.healthdiary.ui.calendar.CalendarFragment
import com.healthdiary.ui.home.HomeFragment
import com.healthdiary.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val homeFragment by inject<HomeFragment>()
    private val calendarFragment by inject<CalendarFragment>()
    private val profileFragment by inject<ProfileFragment>()

    private val bottomNavMap = mapOf(
        R.id.bottom_nav_item_home to homeFragment,
        R.id.bottom_nav_item_calendar to calendarFragment,
        R.id.bottom_nav_item_profile to profileFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayFragment(homeFragment)
        setBottomNavigationListener()
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    private fun setBottomNavigationListener() {
           bottom_nav_home.setOnNavigationItemSelectedListener { menuItem ->
            val fragment = bottomNavMap[menuItem.itemId]
            fragment?.let { displayFragment(it) }
            return@setOnNavigationItemSelectedListener true
        }
    }
}
