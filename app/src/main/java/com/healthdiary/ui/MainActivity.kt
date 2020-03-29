package com.healthdiary.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.healthdiary.R
import com.healthdiary.ui.calendar.CalendarFragment
import com.healthdiary.ui.home.HomeFragment
import com.healthdiary.ui.profile.ProfileFragment
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    val homeFragment = HomeFragment()
    val profileFragment = ProfileFragment()
    val calendarFragment = CalendarFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.plant(Timber.DebugTree())
        displayFragment(homeFragment)
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}
