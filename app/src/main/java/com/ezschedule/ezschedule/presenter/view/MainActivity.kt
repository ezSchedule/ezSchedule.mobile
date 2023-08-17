package com.ezschedule.ezschedule.presenter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ezschedule.ezschedule.R
import com.ezschedule.ezschedule.databinding.ActivityMainBinding
import com.ezschedule.ezschedule.presenter.utils.TokenManager
import com.ezschedule.ezschedule.presenter.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
    }

    override fun onStart() {
        super.onStart()
        TokenManager(this).getToken()?.let {
            displayLoginItems()
            navigateToAdmin()
        }
    }

    private fun navigateToAdmin() {
        Navigation.findNavController(this, R.id.fragment_nav_host)
            .navigate(R.id.action_to_calendar)
    }

    private fun displayLoginItems() {
        with(binding) {
            bottomNavigation.isVisible = true
            includeToolbar.root.isVisible = true
        }
    }

    private fun setupBottomNavigation() {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment)
                .navController
        binding.bottomNavigation.setupWithNavController(navController)
        setupTitleToolbar(navController)
    }

    private fun setupTitleToolbar(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.includeToolbar.viewToolbar.title =
                getString(viewModel.getTitleScreen(destination.id))
        }
    }

}