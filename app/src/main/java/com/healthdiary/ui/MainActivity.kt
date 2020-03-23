package com.healthdiary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.healthdiary.R
import com.healthdiary.ui.home.HomeFragment
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    val homeFragment by lazy { HomeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        Timber.plant(Timber.DebugTree())
        displayFragment(homeFragment)
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}
