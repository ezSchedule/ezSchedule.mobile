package com.ezschedule.ezschedule.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private var _showAdminLayout = MutableLiveData<Unit>()
    val showAdminLayout: LiveData<Unit> = _showAdminLayout

    private var _showUserLayout = MutableLiveData<Unit>()
    val showUserLayout: LiveData<Unit> = _showUserLayout

    fun verifyIsAdmin(isAdmin: Boolean) {
        if (isAdmin) _showAdminLayout.value = Unit
        else _showUserLayout.value = Unit
    }

}