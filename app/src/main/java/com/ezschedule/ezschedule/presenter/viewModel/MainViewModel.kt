package com.ezschedule.ezschedule.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezschedule.ezschedule.R
import com.ezschedule.network.domain.useCase.tenant.LogoutUseCase
import com.ezschedule.network.data.network.wrapper.ResultWrapper
import com.ezschedule.utils.ResourceWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val useCase: LogoutUseCase,
    private val resourceWrapper: ResourceWrapper
) : ViewModel() {
    private var _setSettingsAction = MutableLiveData<Unit>()
    val setSettingsAction: LiveData<Unit> = _setSettingsAction

    private var _setLocationAndMenu = MutableLiveData<Pair<Int, Int>>()
    val setLocationAndMenu: LiveData<Pair<Int, Int>> = _setLocationAndMenu

    private var _setLogoutAction = MutableLiveData<Unit>()
    val setLogoutAction: LiveData<Unit> = _setLogoutAction

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getTitleScreen(id: Int) = when (id) {
        R.id.calendarFragment, R.id.calendarUserFragment -> com.ezschedule.network.R.string.frag_calendar_name
        R.id.dashboardFragment -> com.ezschedule.network.R.string.frag_dashboard_name
        R.id.scheduleUserFragment -> com.ezschedule.network.R.string.frag_schedule_name
        R.id.servicesFragment, R.id.servicesUserFragment -> com.ezschedule.network.R.string.frag_services_name
        R.id.paymentFragment, R.id.historyUserFragment -> com.ezschedule.network.R.string.frag_payments_name
        R.id.forumFragment, R.id.forumUserFragment -> com.ezschedule.network.R.string.frag_forum_name
        R.id.settingsFragment -> com.ezschedule.network.R.string.frag_settings_name
        else -> com.ezschedule.network.R.string.frag_not_found_name
    }

    fun getMenuAction(id: Int, email: String) = when (id) {
        R.id.settingsFragment -> _setSettingsAction.value = Unit

        else -> logoutTenant(email)
    }

    fun validateIsAdmin(admin: Boolean) {
        if (admin) _setLocationAndMenu.value = Pair(R.id.action_to_calendar, R.menu.menu_admin)
        else _setLocationAndMenu.value = Pair(R.id.action_to_calendar_user, R.menu.menu_user)
    }

    fun logoutTenant(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (useCase.execute(email)) {
                is ResultWrapper.Success -> _setLogoutAction.postValue(Unit)

                is ResultWrapper.Error -> resourceWrapper.getString(com.ezschedule.network.R.string.bt_logout_error)
            }
        }
    }
}