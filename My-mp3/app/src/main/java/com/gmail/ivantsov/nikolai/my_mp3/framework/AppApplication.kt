package com.gmail.ivantsov.nikolai.my_mp3.framework

import android.app.Application
import com.gmail.ivantsov.nikolai.my_mp3.BuildConfig
import com.gmail.ivantsov.nikolai.my_mp3.di.*
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(
            androidContext = this,
            listOf(
                viewModelModule,
                dataSourceModule,
                interactorsModule,
                repositoryModule,
                adapterModule,
                mediaPlayerModule
            )
        )
        setLogTimberPlane()
    }

    private fun setLogTimberPlane() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseThree())
        }
    }
}