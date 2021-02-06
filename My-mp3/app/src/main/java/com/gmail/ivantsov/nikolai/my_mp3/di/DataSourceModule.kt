package com.gmail.ivantsov.nikolai.my_mp3.di

import android.content.Context
import com.gmail.ivantsov.nikolai.my_mp3.framework.SongDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val dataSourceModule = module {

    fun provideSongDataSource(context: Context): SongDataSource {
        return SongDataSource(context)
    }

    single {
        provideSongDataSource(androidContext())
    }
}