package com.example.end.presentation.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.end.R
import com.example.end.presentation.presenters.impl.MainViewModel
import com.example.end.presentation.ui.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_products, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        setupDrawer()
        setDrawerEnabled(false)
    }

    private fun setupDrawer() {
        drawerLayout = drawer
        drawerToggle =
            ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close)
        drawer.addDrawerListener(drawerToggle)
    }

    fun openDrawer() {
        drawer.openDrawer(GravityCompat.END)
    }

    fun setDrawerEnabled(enabled: Boolean) {
        val lockMode = if (enabled)
            DrawerLayout.LOCK_MODE_UNLOCKED
        else
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        drawer.setDrawerLockMode(lockMode)
        drawerToggle.isDrawerIndicatorEnabled = enabled
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

}
