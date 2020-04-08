package com.healthdiary.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.healthdiary.R
import com.healthdiary.ui.navigation.TabManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val tabManager: TabManager by lazy { TabManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_nav_home.setOnNavigationItemSelectedListener(this)
        tabManager.currentController = tabManager.navHomeController
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        tabManager.switchTab(item.itemId)
        return true
    }

    override fun onBackPressed() {
        tabManager.onBackPressed()
    }
}
