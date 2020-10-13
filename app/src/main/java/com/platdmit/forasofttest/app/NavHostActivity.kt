package com.platdmit.forasofttest.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.platdmit.forasofttest.R
import com.platdmit.forasofttest.app.base.extentions.setVisibilityStatus
import com.platdmit.forasofttest.app.utilities.ProgressBarHandler
import com.platdmit.forasofttest.databinding.ActivityNavHostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavHostActivity : AppCompatActivity(R.layout.activity_nav_host), ProgressBarHandler {
    private val navHostBinding: ActivityNavHostBinding by viewBinding(R.id.host_root)
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBarInit()
        navigationInit()
    }

    private fun actionBarInit() {
        setSupportActionBar(navHostBinding.toolbar)
    }

    private fun navigationInit() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return (NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp())
    }

    override fun showLoading(status: Boolean) {
        navHostBinding.progressBar.setVisibilityStatus(status)
    }
}