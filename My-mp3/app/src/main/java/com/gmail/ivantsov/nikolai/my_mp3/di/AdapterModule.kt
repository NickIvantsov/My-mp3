package com.gmail.ivantsov.nikolai.my_mp3.di

import com.gmail.ivantsov.nikolai.my_mp3.presentation.library.SongsAdapter
import com.gmail.ivantsov.nikolai.my_mp3.presentation.library.SongsFilter
import org.koin.dsl.module.module

val adapterModule = module {
    fun provideSongsAdapter(songsFilter: SongsFilter): SongsAdapter {
        return SongsAdapter(songsFilter)
    }

    fun provideSongsFilter(): SongsFilter {
        return SongsFilter()
    }
    factory {
        provideSongsAdapter(get())
    }
    factory {
        provideSongsFilter()
    }
}