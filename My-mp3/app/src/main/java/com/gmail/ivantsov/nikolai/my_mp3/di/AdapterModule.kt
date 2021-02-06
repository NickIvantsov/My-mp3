package com.gmail.ivantsov.nikolai.my_mp3.di

import com.gmail.ivantsov.nikolai.my_mp3.presentation.library.SongsAdapter
import org.koin.dsl.module.module

val adapterModule = module {
    fun provideSongsAdapter(): SongsAdapter {
        return SongsAdapter()
    }
    factory {
        provideSongsAdapter()
    }
}