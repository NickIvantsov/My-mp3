package com.gmail.ivantsov.nikolai.my_mp3.di

import com.gmail.ivantsov.nikolai.my_mp3.framework.navigator.Navigator
import org.koin.dsl.module.module

val navigatorModule = module {
    fun provideMediaPlayer(): Navigator {
        return Navigator()
    }
    single {
        provideMediaPlayer()
    }
}