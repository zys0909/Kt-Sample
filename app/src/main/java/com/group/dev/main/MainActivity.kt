package com.group.dev.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.group.dev.R
import com.group.common.base.BaseActivity


/**
 * 描述:首页
 *
 * author zys
 */
class MainActivity : BaseActivity() {


    private val bottomNavView: BottomNavigationView by lazy { findViewById(R.id.bottom_nav_view) }


    override fun layoutId(): Int = R.layout.activity_main

    override fun init(savedInstanceState: Bundle?) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNavView, navController)
    }
}
