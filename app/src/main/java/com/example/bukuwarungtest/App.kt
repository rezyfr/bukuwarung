package com.example.bukuwarungtest

import android.app.Application
import com.example.bukuwarungtest.di.dataModule
import com.example.bukuwarungtest.di.helperModule
import com.example.bukuwarungtest.di.networkModule
import com.example.bukuwarungtest.di.vmModule
import com.example.bukuwarungtest.utils.PrefManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startInject()

        PrefManager.init(this)
    }

    private fun startInject() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    dataModule, helperModule, networkModule, vmModule
                )
            )
        }
    }
}