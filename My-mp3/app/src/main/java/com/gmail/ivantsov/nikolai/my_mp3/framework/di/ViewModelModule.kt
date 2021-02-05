package com.gmail.ivantsov.nikolai.my_mp3.framework.di

import com.gmail.ivantsov.nikolai.my_mp3.presentation.MainViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel {
        MainViewModel()
    }
}