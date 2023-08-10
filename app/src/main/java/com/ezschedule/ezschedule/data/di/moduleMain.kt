package com.ezschedule.ezschedule.data.di

import com.ezschedule.ezschedule.data.repository.LoginRepository
import com.ezschedule.ezschedule.domain.useCase.LoginUseCase
import com.ezschedule.ezschedule.presenter.viewModel.LoginViewModel
import com.ezschedule.network.data.api.LoginEndpoint
import com.ezschedule.network.data.network.NetworkServiceFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleMain = module {
    single { NetworkServiceFactory() }

    factory { get<NetworkServiceFactory>().createNetworkService<LoginEndpoint>() }

    factory { LoginRepository(get()) }

    factory { LoginUseCase(get()) }

    viewModel { LoginViewModel(get()) }
}