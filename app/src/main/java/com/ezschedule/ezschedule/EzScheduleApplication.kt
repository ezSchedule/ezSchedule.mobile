package com.ezschedule.ezschedule

import android.app.Application
import com.ezschedule.ezschedule.data.di.moduleMain
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EzScheduleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@EzScheduleApplication)
            modules(moduleMain)
        }
    }
}