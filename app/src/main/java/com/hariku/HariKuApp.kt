package com.hariku

import android.app.Application
import com.hariku.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HariKuApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HariKuApp)
            modules(appModule)
        }
    }
}

