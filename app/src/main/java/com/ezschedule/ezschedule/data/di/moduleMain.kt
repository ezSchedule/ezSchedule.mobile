package com.ezschedule.ezschedule.data.di

import com.ezschedule.ezschedule.data.repository.TenantRepository
import com.ezschedule.ezschedule.domain.useCase.LoginUseCase
import com.ezschedule.ezschedule.presenter.viewModel.TenantViewModel
import com.ezschedule.network.data.api.TenantEndpoint
import com.ezschedule.network.data.network.NetworkServiceFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleMain = module {
    single { NetworkServiceFactory() }

    factory { get<NetworkServiceFactory>().createNetworkService<TenantEndpoint>() }

    factory { TenantRepository(get()) }

    factory { LoginUseCase(get()) }

    viewModel { TenantViewModel(get()) }
}