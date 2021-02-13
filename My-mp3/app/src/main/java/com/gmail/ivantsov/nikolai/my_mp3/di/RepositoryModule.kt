package com.gmail.ivantsov.nikolai.my_mp3.di

import com.gmail.ivantsov.nikolai.core.data.ISongDataSource
import com.gmail.ivantsov.nikolai.core.data.ISongRepository
import com.gmail.ivantsov.nikolai.core.data.SongRepository
import com.gmail.ivantsov.nikolai.my_mp3.framework.SongDataSource
import org.koin.dsl.module.module

val repositoryModule = module {

    fun provideSongRepository(songDataSource: ISongDataSource): SongRepository {
        return SongRepository(songDataSource)
    }
    single { provideSongRepository(get<SongDataSource>()) } bind ISongRepository::class

}