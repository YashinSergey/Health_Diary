package com.healthdiary.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.healthdiary.R
import com.healthdiary.extensions.display
import com.healthdiary.ui.calendar.CalendarFragment
import com.healthdiary.ui.home.HomeFragment
import com.healthdiary.ui.indicator.IndicatorFragment
import com.healthdiary.ui.profile.ProfileFragment
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class MainActivity : AppCompatActivity() {

    private val homeFragment by inject<HomeFragment>()
    private val calendarFragment by inject<CalendarFragment>()
    private val profileFragment by inject<ProfileFragment>()
    private val indicatorFragment = IndicatorFragment()

    private val bottomNavMap = mapOf(
        R.id.bottom_nav_item_home to homeFragment,
        R.id.bottom_nav_item_calendar to calendarFragment,
        R.id.bottom_nav_item_profile to profileFragment
    )

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayFragment(homeFragment)
        setBottomNavigationListener()
        homeFragment.clickAdapterSubject.subscribe(getHomeAdapterConsumer())
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.display(R.id.container, fragment, FragmentTransaction.TRANSIT_NONE)
    }

    private fun setBottomNavigationListener() {
           bottom_nav_home.setOnNavigationItemSelectedListener { menuItem ->
            val fragment = bottomNavMap[menuItem.itemId]
            fragment?.let { displayFragment(it) }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun getHomeAdapterConsumer(): Consumer<Int> {
        return Consumer {
            indicatorFragment.apply {
                arguments = bundleOf("INDICATOR_ID" to it)
            }
            displayFragment(indicatorFragment)
        }
    }
}
