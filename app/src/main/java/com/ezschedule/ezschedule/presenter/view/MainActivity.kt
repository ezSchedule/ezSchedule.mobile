package com.ezschedule.ezschedule.presenter.view

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
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
import com.ezschedule.network.domain.presentation.TenantPresentation
import com.ezschedule.utils.SharedPreferencesManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var popupMenu: PopupMenu
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var user: TenantPresentation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        user = SharedPreferencesManager(this).getInfo()
        setContentView(binding.root)
        setupObservers()
        setupPopupMenu()
        setupOnClickMenu()
    }

    override fun onStart() {
        super.onStart()
        TokenManager(this).getToken()?.let { token ->
            TokenManager(this).decoded(token) { email ->
                setupDialogLogout(email)
            }
            viewModel.validateIsAdmin(user.isAdmin)
            Firebase.messaging.subscribeToTopic("conversations-${user.idCondominium}")
        }
    }

    private fun setupDialogLogout(email: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_logout_tv_title))
            .setMessage(getString(R.string.dialog_logout_tv_description))
            .setNeutralButton(getString(R.string.dialog_logout_btn_confirm)) { _, _ ->
                viewModel.logoutTenant(email)
            }
            .create()
            .show()
    }

    fun displayLoginItems(isVisible: Boolean) {
        with(binding) {
            bottomNavigation.isVisible = isVisible
            includeToolbar.root.isVisible = isVisible
            setImageUser(includeToolbar.ivUser)
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

    private fun setImageUser(imageView: ImageView) {
        viewModel.getImage(SharedPreferencesManager(this).getInfo().image)?.let {
            Glide.with(this)
                .load(it)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(imageView)
        }
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        finish()
    }

}