package com.gmail.ivantsov.nikolai.my_mp3.framework

import android.app.Application
import com.gmail.ivantsov.nikolai.my_mp3.framework.di.dataSourceModule
import com.gmail.ivantsov.nikolai.my_mp3.framework.di.interactorsModule
import com.gmail.ivantsov.nikolai.my_mp3.framework.di.repositoryModule
import com.gmail.ivantsov.nikolai.my_mp3.framework.di.viewModelModule
import org.koin.android.ext.android.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(
            androidContext = this,
            listOf(
                viewModelModule,
                dataSourceModule,
                interactorsModule,
                repositoryModule
            )
        )
    }
}