package com.ezschedule.ezschedule.presenter.view

import android.os.Build
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.ezschedule.ezschedule.R
import com.ezschedule.ezschedule.databinding.ActivityMainBinding
import com.ezschedule.ezschedule.presenter.utils.TokenManager
import com.ezschedule.ezschedule.presenter.viewModel.MainViewModel
import com.ezschedule.utils.SharedPreferencesManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var popupMenu: PopupMenu
    private val viewModel by viewModel<MainViewModel>()
    private val user by lazy {
        SharedPreferencesManager(this).getInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setupPopupMenu()
        setupOnClickMenu()
    }

    override fun onStart() {
        super.onStart()
        with(TokenManager(this)) {
            getToken()?.let { token ->
                decoded(token) { email ->
                    setupDialogLogout(email)
                }
                viewModel.validateIsAdmin(user.isAdmin)
                setImageUser(user.image)
            }
        }
        Firebase.messaging.subscribeToTopic("conversations-${user.idCondominium}")
    }

    private fun setupDialogLogout(email: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(com.ezschedule.network.R.string.dialog_logout_tv_title))
            .setMessage(getString(com.ezschedule.network.R.string.dialog_logout_tv_description))
            .setNeutralButton(getString(com.ezschedule.network.R.string.dialog_logout_btn_confirm)) { _, _ ->
                viewModel.logoutTenant(email)
            }
            .create()
            .show()
    }

    fun displayLoginItems(isVisible: Boolean) {
        with(binding) {
            bottomNavigation.isVisible = isVisible
            includeToolbar.root.isVisible = isVisible
        }
    }

    private fun setupObservers() = with(viewModel) {
        setSettingsAction.observe(this@MainActivity) {
            navigateTo(R.id.action_to_settings)
            displayLoginItems(false)
        }
        setLocationAndMenu.observe(this@MainActivity) {
            navigateTo(it.first)
            setupBottomNavigation(it.second)
            displayLoginItems(isVisible = true)
        }
        setLogoutAction.observe(this@MainActivity) {
            displayLoginItems(isVisible = false)
            navigateTo(R.id.loginFragment)
            SharedPreferencesManager(this@MainActivity).deleteInfo()
        }
        error.observe(this@MainActivity) {
            showSnackBarMessage(it)
        }
    }

    private fun setupPopupMenu() {
        popupMenu = PopupMenu(applicationContext, binding.includeToolbar.ivUser)
        popupMenu.apply {
            menuInflater.inflate(R.menu.menu_popup, popupMenu.menu)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) setForceShowIcon(true)
            setOnMenuItemClickListener {
                viewModel.getMenuAction(
                    it.itemId,
                    SharedPreferencesManager(this@MainActivity).getInfo().email
                )
                true
            }
        }
    }

    fun setupBottomNavigation(menuId: Int) {
        binding.bottomNavigation.apply {
            menu.clear()
            inflateMenu(menuId)
            setOnItemSelectedListener {
                navigateTo(it.itemId)
                setupTitleToolbar(it.itemId)
                true
            }
        }
    }

    private fun setupTitleToolbar(destinationId: Int) {
        binding.includeToolbar.viewToolbar.title =
            getString(viewModel.getTitleScreen(destinationId))
    }

    private fun setupOnClickMenu() = binding.includeToolbar.ivUser.setOnClickListener {
        popupMenu.show()
    }

    private fun navigateTo(navigationId: Int) {
        Navigation.findNavController(this, R.id.fragment_nav_host)
            .navigate(navigationId)
    }

    fun setImageUser(image: String?) {
        image?.let {
            Glide.with(this)
                .load(it)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(binding.includeToolbar.ivUser)
        }
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}