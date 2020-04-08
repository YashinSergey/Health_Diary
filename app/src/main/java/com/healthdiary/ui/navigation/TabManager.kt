package com.healthdiary.ui.navigation

import android.view.View
import androidx.core.view.isInvisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.healthdiary.R
import com.healthdiary.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class TabManager(private val mainActivity: MainActivity) {

    private val startDestinations = mapOf(
            R.id.bottom_nav_item_home to R.id.homeFragment,
            R.id.bottom_nav_item_calendar to R.id.calendarFragment,
            R.id.bottom_nav_item_profile to R.id.profileFragment
    )

    private var currentTabId: Int = R.id.bottom_nav_item_home
    var currentController: NavController? = null
    private var tabHistory = TabHistory().apply { push(R.id.bottom_nav_item_home) }

    val navHomeController: NavController by lazy {
        mainActivity.findNavController(R.id.homeTab).apply {
            graph = navInflater.inflate(R.navigation.nav_graph).apply {
                startDestination = startDestinations.getValue(R.id.bottom_nav_item_home)
            }
        }
    }
    private val navCalendarController: NavController by lazy {
        mainActivity.findNavController(R.id.calendarTab).apply {
            graph = navInflater.inflate(R.navigation.nav_graph).apply {
                startDestination = startDestinations.getValue(R.id.bottom_nav_item_calendar)
            }
        }
    }
    private val navProfileController: NavController by lazy {
        mainActivity.findNavController(R.id.profileTab).apply {
            graph = navInflater.inflate(R.navigation.nav_graph).apply {
                startDestination = startDestinations.getValue(R.id.bottom_nav_item_profile)
            }
        }
    }
    private val homeTabContainer: View by lazy { mainActivity.homeTabContainer }
    private val calendarTabContainer: View by lazy { mainActivity.calendarTabContainer }
    private val profileTabContainer: View by lazy { mainActivity.profileTabContainer }

    fun onBackPressed() {
        currentController?.let {
            if (it.currentDestination == null || it.currentDestination?.id == startDestinations.getValue(currentTabId)) {
                if (tabHistory.size > 1) {
                    val tabId = tabHistory.popPrevious()
                    switchTab(tabId, false)
                    mainActivity.bottom_nav_home.menu.findItem(tabId)?.isChecked = true
                } else {
                    mainActivity.finish()
                }
            }
            it.popBackStack()
        } ?: run {
            mainActivity.finish()
        }
    }

    fun switchTab(tabId: Int, addToHistory: Boolean = true) {
        currentTabId = tabId

        when (tabId) {
            R.id.bottom_nav_item_home -> {
                if (currentController == navHomeController){
                    navHomeController.popBackStack()
                    navHomeController.navigate(R.id.homeFragment)
                    return
                }
                currentController = navHomeController
                invisibleTabContainerExcept(homeTabContainer)
            }
            R.id.bottom_nav_item_calendar -> {
                currentController = navCalendarController
                invisibleTabContainerExcept(calendarTabContainer)
            }
            R.id.bottom_nav_item_profile -> {
                currentController = navProfileController
                invisibleTabContainerExcept(profileTabContainer)
            }
        }
        if (addToHistory) {
            tabHistory.push(tabId)
        }
    }

    private fun invisibleTabContainerExcept(container: View) {
        /**
         * Если ругается компилятор:
         * Settings -> Other Settings -> Kotlin Compiler -> Target JVM -> 1.8
         */
        homeTabContainer.isInvisible = true
        calendarTabContainer.isInvisible = true
        profileTabContainer.isInvisible = true

        container.isInvisible = false
    }
}