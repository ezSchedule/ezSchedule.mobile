package com.ezschedule.ezschedule

import android.app.Application
import com.ezschedule.ezschedule.data.di.moduleMain
import org.koin.core.context.startKoin

class TenantApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            modules(moduleMain)
        }
    }
}