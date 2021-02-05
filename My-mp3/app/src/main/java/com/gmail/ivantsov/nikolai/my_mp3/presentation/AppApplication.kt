package com.gmail.ivantsov.nikolai.my_mp3.presentation

import android.app.Application
import com.gmail.ivantsov.nikolai.my_mp3.framework.di.viewModelModule
import org.koin.android.ext.android.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(
            androidContext = this,
            listOf(
                viewModelModule
            )
        )
    }
}