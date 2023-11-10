package com.ezschedule.ezschedule.data.di

import android.content.Context
import com.ezschedule.admin.data.repository.NotificationRepository
import com.ezschedule.admin.data.repository.ScheduleRepository
import com.ezschedule.admin.data.repository.ServiceRepository
import com.ezschedule.admin.domain.useCase.CalendarUseCase
import com.ezschedule.admin.domain.useCase.CreateServiceUseCase
import com.ezschedule.admin.domain.useCase.DashboardUseCase
import com.ezschedule.admin.domain.useCase.ForumUseCase
import com.ezschedule.admin.domain.useCase.GetServiceListUseCase
import com.ezschedule.admin.domain.useCase.GetTenantsListUseCase
import com.ezschedule.admin.domain.useCase.SendNotificationUseCase
import com.ezschedule.admin.presenter.viewmodel.CalendarViewModel
import com.ezschedule.admin.presenter.viewmodel.DashboardViewModel
import com.ezschedule.admin.presenter.viewmodel.ForumViewModel
import com.ezschedule.admin.presenter.viewmodel.ServicesViewModel
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
import com.ezschedule.network.data.api.CalendarEndpoint
import com.ezschedule.network.data.api.CondominiumEndpoint
import com.ezschedule.network.data.api.NotificationEndpoint
import com.ezschedule.network.data.api.SaloonEndpoint
import com.ezschedule.network.data.api.ServicesEndpoint
import com.ezschedule.network.data.api.TenantEndpoint
import com.ezschedule.network.data.network.NetworkServiceFactory
import com.ezschedule.user.data.repository.ScheduleUserRepository
import com.ezschedule.user.data.repository.ServiceUserRepository
import com.ezschedule.user.domain.useCase.ForumUserUseCase
import com.ezschedule.user.domain.useCase.ScheduleUserUseCase
import com.ezschedule.user.domain.useCase.ServiceUserUseCase
import com.ezschedule.user.presenter.viewModel.ForumUserViewModel
import com.ezschedule.user.presenter.viewModel.ScheduleUserViewModel
import com.ezschedule.user.presenter.viewModel.ServiceUserViewModel
import com.ezschedule.utils.ResourceWrapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleMain = module {
    single { NetworkServiceFactory() }

    factory { ResourceWrapper(get() as Context) }

    factory { get<NetworkServiceFactory>().createNetworkService<TenantEndpoint>(get() as Context) }
    factory { get<NetworkServiceFactory>().createNetworkService<CalendarEndpoint>(get() as Context) }
    factory { get<NetworkServiceFactory>().createNetworkService<CondominiumEndpoint>(get() as Context) }
    factory { get<NetworkServiceFactory>().createNetworkService<SaloonEndpoint>(get() as Context) }
    factory { get<NetworkServiceFactory>().createNetworkService<NotificationEndpoint>(get() as Context) }
    factory { get<NetworkServiceFactory>().createNetworkService<ServicesEndpoint>(get() as Context) }

    factory { TenantRepository(get()) }
    factory { ScheduleRepository(get()) }
    factory { CondominiumRepository(get()) }
    factory { SaloonRepository(get()) }
    factory { NotificationRepository(get()) }
    factory { ServiceRepository(get()) }
    factory { ServiceUserRepository(get()) }
    factory { ScheduleUserRepository(get()) }

    factory { SendNotificationUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { CalendarUseCase(get()) }
    factory { GetTenantSettingsUseCase(get()) }
    factory { GetCondominiumSettingsUseCase(get()) }
    factory { UpdateTenantSettingsUseCase(get()) }
    factory { CreateSaloonUseCase(get()) }
    factory { CreateServiceUseCase(get()) }
    factory { DashboardUseCase(get()) }
    factory { ForumUseCase() }
    factory { ForumUserUseCase() }
    factory { ServiceUserUseCase(get()) }
    factory { GetTenantsListUseCase(get()) }
    factory { GetServiceListUseCase(get()) }
    factory { ScheduleUserUseCase(get()) }

    viewModel { TenantViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { CalendarViewModel(get()) }
    viewModel { SettingsViewModel(get(), get(), get(), get()) }
    viewModel { ServicesViewModel(get(), get(), get()) }
    viewModel { DashboardViewModel(get()) }
    viewModel { ForumViewModel(get(), get()) }
    viewModel { ServiceUserViewModel(get()) }
    viewModel { ForumUserViewModel(get()) }
    viewModel { ScheduleUserViewModel(get()) }
}