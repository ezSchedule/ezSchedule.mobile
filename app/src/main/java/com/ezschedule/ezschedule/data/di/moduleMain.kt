package com.ezschedule.ezschedule.data.di

import android.content.Context
import com.ezschedule.network.data.repository.NotificationRepository
import com.ezschedule.network.data.repository.ScheduleRepository
import com.ezschedule.network.domain.useCase.schedule.CalendarUseCase
import com.ezschedule.network.domain.useCase.service.CreateServiceUseCase
import com.ezschedule.network.domain.useCase.schedule.DashboardUseCase
import com.ezschedule.network.domain.useCase.firebase.ForumUseCase
import com.ezschedule.network.domain.useCase.pix.GetAllPixAttempts
import com.ezschedule.network.domain.useCase.service.GetServiceListUseCase
import com.ezschedule.network.domain.useCase.service.GetTenantsListUseCase
import com.ezschedule.network.domain.useCase.firebase.HistoryUseCase
import com.ezschedule.network.domain.useCase.notification.SendNotificationUseCase
import com.ezschedule.admin.presenter.viewmodel.CalendarViewModel
import com.ezschedule.admin.presenter.viewmodel.DashboardViewModel
import com.ezschedule.admin.presenter.viewmodel.ForumViewModel
import com.ezschedule.admin.presenter.viewmodel.HistoryViewModel
import com.ezschedule.admin.presenter.viewmodel.ServicesViewModel
import com.ezschedule.network.data.repository.CondominiumRepository
import com.ezschedule.network.data.repository.SaloonRepository
import com.ezschedule.network.data.repository.TenantRepository
import com.ezschedule.network.domain.useCase.saloon.CreateSaloonUseCase
import com.ezschedule.network.domain.useCase.condominium.GetCondominiumSettingsUseCase
import com.ezschedule.network.domain.useCase.tenant.GetTenantSettingsUseCase
import com.ezschedule.network.domain.useCase.tenant.LoginUseCase
import com.ezschedule.network.domain.useCase.tenant.LogoutUseCase
import com.ezschedule.network.domain.useCase.tenant.UpdateTenantSettingsUseCase
import com.ezschedule.ezschedule.presenter.viewModel.MainViewModel
import com.ezschedule.ezschedule.presenter.viewModel.SettingsViewModel
import com.ezschedule.ezschedule.presenter.viewModel.TenantViewModel
import com.ezschedule.network.data.api.CondominiumEndpoint
import com.ezschedule.network.data.api.NotificationEndpoint
import com.ezschedule.network.data.api.PixEndpoint
import com.ezschedule.network.data.api.SaloonEndpoint
import com.ezschedule.network.data.api.ScheduleEndpoint
import com.ezschedule.network.data.api.ServicesEndpoint
import com.ezschedule.network.data.api.TenantEndpoint
import com.ezschedule.network.data.network.NetworkServiceFactory
import com.ezschedule.network.data.repository.PixRepository
import com.ezschedule.network.data.repository.ServiceRepository
import com.ezschedule.network.domain.useCase.schedule.CalendarUserUseCase
import com.ezschedule.network.domain.useCase.schedule.CreateScheduleUseCase
import com.ezschedule.network.domain.useCase.firebase.FirestoreUseCase
import com.ezschedule.network.domain.useCase.saloon.GetSaloonUseCase
import com.ezschedule.network.domain.useCase.tenant.GetTenantByIdUseCase
import com.ezschedule.network.domain.useCase.pix.PixUseCase
import com.ezschedule.network.domain.useCase.schedule.ScheduleUserUseCase
import com.ezschedule.network.domain.useCase.service.ServiceUserUseCase
import com.ezschedule.network.domain.useCase.pix.UserGetAllPixAttempts
import com.ezschedule.network.domain.useCase.schedule.ValidateScheduleUseCase
import com.ezschedule.user.presenter.viewModel.CalendarUserViewModel
import com.ezschedule.user.presenter.viewModel.ForumUserViewModel
import com.ezschedule.user.presenter.viewModel.HistoryUserViewModel
import com.ezschedule.user.presenter.viewModel.NewDateViewModel
import com.ezschedule.user.presenter.viewModel.ScheduleUserViewModel
import com.ezschedule.user.presenter.viewModel.ServiceUserViewModel
import com.ezschedule.utils.ResourceWrapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleMain = module {
    single { NetworkServiceFactory() }

    factory { ResourceWrapper(get() as Context) }

    factory { get<NetworkServiceFactory>().createNetworkService<TenantEndpoint>(get() as Context) }
    factory { get<NetworkServiceFactory>().createNetworkService<ScheduleEndpoint>(get() as Context) }
    factory { get<NetworkServiceFactory>().createNetworkService<CondominiumEndpoint>(get() as Context) }
    factory { get<NetworkServiceFactory>().createNetworkService<SaloonEndpoint>(get() as Context) }
    factory { get<NetworkServiceFactory>().createNetworkService<NotificationEndpoint>(get() as Context) }
    factory { get<NetworkServiceFactory>().createNetworkService<ServicesEndpoint>(get() as Context) }
    factory { get<NetworkServiceFactory>().createNetworkService<PixEndpoint>(get() as Context) }

    factory { CondominiumRepository(get()) }
    factory { NotificationRepository(get()) }
    factory { PixRepository(get()) }
    factory { SaloonRepository(get()) }
    factory { ScheduleRepository(get()) }
    factory { ServiceRepository(get()) }
    factory { TenantRepository(get()) }

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
    factory { FirestoreUseCase() }
    factory { ForumUseCase() }
    factory { ServiceUserUseCase(get()) }
    factory { GetTenantsListUseCase(get()) }
    factory { GetServiceListUseCase(get()) }
    factory { HistoryUseCase() }
    factory { HistoryUserViewModel(get(), get(), get()) }
    factory { ScheduleUserUseCase(get()) }
    factory { CalendarUserUseCase(get()) }
    factory { GetSaloonUseCase(get()) }
    factory { CreateScheduleUseCase(get()) }
    factory { GetTenantByIdUseCase(get()) }
    factory { PixUseCase(get()) }
    factory { UserGetAllPixAttempts(get()) }
    factory { ValidateScheduleUseCase(get()) }
    factory { GetAllPixAttempts(get()) }

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
    viewModel { CalendarUserViewModel(get()) }
    viewModel { NewDateViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { HistoryViewModel(get(), get()) }
}