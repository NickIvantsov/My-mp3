package com.gmail.ivantsov.nikolai.my_mp3.di

import android.app.Application
import com.gmail.ivantsov.nikolai.my_mp3.framework.Interactors
import com.gmail.ivantsov.nikolai.my_mp3.framework.SongModelDataMapper
import com.gmail.ivantsov.nikolai.my_mp3.presentation.library.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    fun provideMainViewModel(
        application: Application,
        interactors: Interactors,
        songModelDataMapper: SongModelDataMapper
    ): MainViewModel {
        return MainViewModel(application, interactors, songModelDataMapper)
    }

    fun provideSongModelDataMapper(): SongModelDataMapper {
        return SongModelDataMapper()
    }
    single {
        provideSongModelDataMapper()
    }
    viewModel {
        provideMainViewModel(androidApplication(), get(), get())
    }

}