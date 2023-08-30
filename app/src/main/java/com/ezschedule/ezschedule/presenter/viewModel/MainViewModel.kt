package com.ezschedule.ezschedule.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.ezschedule.R
import com.ezschedule.ezschedule.domain.useCase.LogoutUseCase
import com.ezschedule.utils.ResourceWrapper
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val useCase: LogoutUseCase,
    private val resourceWrapper: ResourceWrapper
) : ViewModel() {
    private var _setSettingsAction = MutableLiveData<Unit>()
    val setSettingsAction: LiveData<Unit> = _setSettingsAction

    private var _setLogoutAction = MutableLiveData<Unit>()
    val setLogoutAction: LiveData<Unit> = _setLogoutAction

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getTitleScreen(id: Int) = when (id) {
        R.id.calendarFragment -> R.string.frag_calendar_name
        R.id.dashboardFragment -> R.string.frag_dashboard_name
        R.id.servicesFragment -> R.string.frag_services_name
        R.id.paymentFragment -> R.string.frag_payments_name
        R.id.forumFragment -> R.string.frag_forum_name
        R.id.settingsFragment -> R.string.frag_settings_name
        else -> R.string.frag_not_found_name
    }

    fun getMenuAction(id: Int, email: String) = when (id) {
        R.id.settingsFragment -> _setSettingsAction.value = Unit

        else -> logoutTenant(email)
    }

    fun getImage(image: String): String? {
        if (image.isNotEmpty()) return image
        return null
    }

    private fun logoutTenant(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (useCase.execute(email)) {
                is ResultWrapper.Success -> _setLogoutAction.postValue(Unit)

                is ResultWrapper.Error -> resourceWrapper.getString(R.string.bt_logout_error)
            }
        }
    }
}