package com.gmail.ivantsov.nikolai.my_mp3.di

import android.content.Context
import android.media.MediaPlayer
import com.gmail.ivantsov.nikolai.core.data.SongRepository
import com.gmail.ivantsov.nikolai.core.domain.musicPlayer.IMusicPlayer
import com.gmail.ivantsov.nikolai.core.interactors.*
import com.gmail.ivantsov.nikolai.my_mp3.framework.Interactors
import com.gmail.ivantsov.nikolai.my_mp3.framework.MusicPlayerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val interactorsModule = module {
    fun provideInteractors(
        getSongs: GetSongs,
        musicPlayer: MusicPlayer
    ): Interactors {
        return Interactors(
            getSongs,
            musicPlayer
        )
    }

    fun provideGetSongs(songRepository: SongRepository): GetSongs {
        return GetSongs(songRepository)
    }

    fun provideMusicPlay(
        songRepository: SongRepository,
        musicPlayer: IMusicPlayer
    ): MusicPlayer {
        return MusicPlayer(songRepository, musicPlayer)
    }

    fun provideMusicPlayImpl(context: Context, mediaPlayer: MediaPlayer): MusicPlayerImpl {
        return MusicPlayerImpl(context, mediaPlayer)
    }
    single { provideInteractors(get(), get()) }
    single { provideGetSongs(get()) }
    single { provideMusicPlay(get(),get<MusicPlayerImpl>()) }
    single { provideMusicPlayImpl(androidContext(), get()) }
}