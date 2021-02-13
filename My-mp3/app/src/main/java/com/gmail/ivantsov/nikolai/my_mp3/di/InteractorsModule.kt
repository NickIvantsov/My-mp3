package com.gmail.ivantsov.nikolai.my_mp3.di

import android.content.Context
import android.media.MediaPlayer
import com.gmail.ivantsov.nikolai.core.data.SongRepository
import com.gmail.ivantsov.nikolai.core.interactors.*
import com.gmail.ivantsov.nikolai.my_mp3.framework.Interactors
import com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer.impl.MusicPlayerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val interactorsModule = module {
    fun provideInteractors(
        getSongs: GetSongs
    ): Interactors {
        return Interactors(
            getSongs
        )
    }

    fun provideGetSongs(songRepository: SongRepository): GetSongs {
        return GetSongs(songRepository)
    }


    fun provideMusicPlayImpl(context: Context, mediaPlayer: MediaPlayer): MusicPlayerImpl {
        return MusicPlayerImpl(context, mediaPlayer)
    }
    single { provideInteractors(get()) }
    single { provideGetSongs(get()) }
    single { provideMusicPlayImpl(androidContext(), get()) }
}