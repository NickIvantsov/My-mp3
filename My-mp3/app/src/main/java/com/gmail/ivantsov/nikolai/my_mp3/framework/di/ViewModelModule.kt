package com.gmail.ivantsov.nikolai.my_mp3.framework.di

import android.app.Application
import android.media.MediaPlayer
import com.gmail.ivantsov.nikolai.my_mp3.framework.Interactors
import com.gmail.ivantsov.nikolai.my_mp3.presentation.library.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    fun provideMainViewModel(
        application: Application,
        interactors: Interactors,
        mediaPlayer: MediaPlayer
    ): MainViewModel {
        return MainViewModel(application, interactors, mediaPlayer)
    }

    viewModel {
        provideMainViewModel(androidApplication(), get(), get())
    }

}