package com.example.bukuwarungtest

import android.R
import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.core.app.NotificationManagerCompat
import com.example.bukuwarungtest.di.dataModule
import com.example.bukuwarungtest.di.helperModule
import com.example.bukuwarungtest.di.networkModule
import com.example.bukuwarungtest.di.vmModule
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
    }

    private fun startInject() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(
                dataModule, helperModule, networkModule, vmModule
            ))
        }
    }

}