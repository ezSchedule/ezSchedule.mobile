package com.ezschedule.ezschedule.data.di

import android.content.Context
import com.ezschedule.admin.data.repository.ScheduleRepository
import com.ezschedule.admin.domain.useCase.CalendarUseCase
import com.ezschedule.admin.presenter.viewmodel.CalendarViewModel
import com.ezschedule.ezschedule.data.repository.TenantRepository
import com.ezschedule.ezschedule.domain.useCase.LoginUseCase
import com.ezschedule.ezschedule.domain.useCase.LogoutUseCase
import com.ezschedule.utils.ResourceWrapper
import com.ezschedule.ezschedule.presenter.viewModel.MainViewModel
import com.ezschedule.ezschedule.presenter.viewModel.SettingsViewModel
import com.ezschedule.ezschedule.presenter.viewModel.TenantViewModel
import com.ezschedule.network.data.api.CalendarEndpoint
import com.ezschedule.network.data.api.TenantEndpoint
import com.ezschedule.network.data.network.NetworkServiceFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleMain = module {
    single { NetworkServiceFactory() }

    factory { ResourceWrapper(get() as Context) }

    factory { get<NetworkServiceFactory>().createNetworkService<TenantEndpoint>() }
    factory { get<NetworkServiceFactory>().createNetworkService<CalendarEndpoint>() }

    factory { TenantRepository(get()) }
    factory { ScheduleRepository(get()) }

    factory { LoginUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { CalendarUseCase(get()) }

    viewModel { TenantViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { CalendarViewModel(get()) }

    viewModel { SettingsViewModel() }
}