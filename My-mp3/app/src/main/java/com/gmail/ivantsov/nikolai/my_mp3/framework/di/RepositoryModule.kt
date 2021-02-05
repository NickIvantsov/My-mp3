package com.gmail.ivantsov.nikolai.my_mp3.framework.di

import com.gmail.ivantsov.nikolai.core.data.SongRepository
import com.gmail.ivantsov.nikolai.my_mp3.framework.SongDataSource
import org.koin.dsl.module.module

val repositoryModule = module {

    fun provideSongRepository(songDataSource: SongDataSource): SongRepository {
        return SongRepository(songDataSource)
    }
    single { provideSongRepository(get()) }

}