package com.ezschedule.ezschedule.presenter.viewModel

import androidx.lifecycle.ViewModel
import com.ezschedule.ezschedule.R

class MainViewModel : ViewModel() {
    fun getTitleScreen(id: Int) = when (id) {
        R.id.calendarFragment -> R.string.frag_calendar_name
        R.id.dashboardFragment -> R.string.frag_dashboard_name
        R.id.servicesFragment -> R.string.frag_services_name
        R.id.paymentFragment -> R.string.frag_payments_name
        R.id.forumFragment -> R.string.frag_forum_name
        else -> R.string.frag_not_found_name
    }
}