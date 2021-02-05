package com.gmail.ivantsov.nikolai.my_mp3.framework.di

import com.gmail.ivantsov.nikolai.core.data.SongRepository
import com.gmail.ivantsov.nikolai.core.interactors.GetSongs
import com.gmail.ivantsov.nikolai.my_mp3.framework.Interactors
import org.koin.dsl.module.module

val interactorsModule = module {
    fun provideInteractors(getSongs: GetSongs): Interactors {
        return Interactors(getSongs)
    }
    fun provideGetSongs(songRepository: SongRepository): GetSongs {
        return GetSongs(songRepository)
    }
    single { provideInteractors(get()) }
    single { provideGetSongs(get()) }
}