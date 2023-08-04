package com.ezschedule.ezschedule.data.di

import com.ezschedule.ezschedule.data.repository.TenantRepository
import com.ezschedule.ezschedule.domain.useCase.GetTenantsUseCase
import com.ezschedule.ezschedule.presenter.viewModel.TenantViewModel
import com.ezschedule.network.data.api.TenantEndpoint
import com.ezschedule.network.data.network.NetworkServiceFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleNetwork = module {
    single { NetworkServiceFactory() }

    factory { get<NetworkServiceFactory>().createNetworkService<TenantEndpoint>() }

    factory { TenantRepository(get()) }

    factory { GetTenantsUseCase(get()) }

    viewModel { TenantViewModel(get()) }
}