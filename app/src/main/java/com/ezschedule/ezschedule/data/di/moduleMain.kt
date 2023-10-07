package com.ezschedule.ezschedule.data.di

import android.content.Context
import com.ezschedule.admin.data.repository.ScheduleRepository
import com.ezschedule.admin.domain.useCase.CalendarUseCase
import com.ezschedule.admin.presenter.viewmodel.CalendarViewModel
import com.ezschedule.admin.presenter.viewmodel.HistoryViewModel
import com.ezschedule.ezschedule.data.repository.CondominiumRepository
import com.ezschedule.ezschedule.data.repository.SaloonRepository
import com.ezschedule.ezschedule.data.repository.TenantRepository
import com.ezschedule.ezschedule.domain.useCase.CreateSaloonUseCase
import com.ezschedule.ezschedule.domain.useCase.GetCondominiumSettingsUseCase
import com.ezschedule.ezschedule.domain.useCase.GetTenantSettingsUseCase
import com.ezschedule.ezschedule.domain.useCase.LoginUseCase
import com.ezschedule.ezschedule.domain.useCase.LogoutUseCase
import com.ezschedule.ezschedule.domain.useCase.UpdateTenantSettingsUseCase
import com.ezschedule.ezschedule.presenter.viewModel.MainViewModel
import com.ezschedule.ezschedule.presenter.viewModel.SettingsViewModel
import com.ezschedule.ezschedule.presenter.viewModel.TenantViewModel
import com.ezschedule.network.data.api.CondominiumEndpoint
import com.ezschedule.network.data.api.SaloonEndpoint
import com.ezschedule.network.data.api.ScheduleEndpoint
import com.ezschedule.network.data.api.TenantEndpoint
import com.ezschedule.network.data.network.NetworkServiceFactory
import com.ezschedule.utils.ResourceWrapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleMain = module {
    single { NetworkServiceFactory() }

    factory { ResourceWrapper(get() as Context) }

    factory { get<NetworkServiceFactory>().createNetworkService<TenantEndpoint>() }
    factory { get<NetworkServiceFactory>().createNetworkService<ScheduleEndpoint>() }
    factory { get<NetworkServiceFactory>().createNetworkService<CondominiumEndpoint>() }
    factory { get<NetworkServiceFactory>().createNetworkService<SaloonEndpoint>() }

    factory { TenantRepository(get()) }
    factory { ScheduleRepository(get()) }
    factory { CondominiumRepository(get()) }
    factory { SaloonRepository(get()) }

    factory { LoginUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { CalendarUseCase(get()) }
    factory { GetTenantSettingsUseCase(get()) }
    factory { GetCondominiumSettingsUseCase(get()) }
    factory { UpdateTenantSettingsUseCase(get()) }
    factory { CreateSaloonUseCase(get()) }

    viewModel { TenantViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { CalendarViewModel(get()) }
    viewModel { SettingsViewModel(get(), get(), get(), get()) }
    viewModel { HistoryViewModel(get()) }
}