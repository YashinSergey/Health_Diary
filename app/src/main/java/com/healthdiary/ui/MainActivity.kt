package com.healthdiary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.healthdiary.R
import com.healthdiary.ui.home.HomeFragment
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        displayFragment(homeFragment)
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}
