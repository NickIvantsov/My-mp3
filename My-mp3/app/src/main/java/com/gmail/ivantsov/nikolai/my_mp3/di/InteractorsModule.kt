package com.gmail.ivantsov.nikolai.my_mp3.di

import android.content.Context
import android.media.MediaPlayer
import com.gmail.ivantsov.nikolai.core.data.SongRepository
import com.gmail.ivantsov.nikolai.core.interactors.GetSongs
import com.gmail.ivantsov.nikolai.core.interactors.SongPlay
import com.gmail.ivantsov.nikolai.my_mp3.framework.Interactors
import com.gmail.ivantsov.nikolai.my_mp3.framework.MusicPlay
import com.gmail.ivantsov.nikolai.core.domain.IPlaySong
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val interactorsModule = module {
    fun provideInteractors(getSongs: GetSongs, songPlay: SongPlay): Interactors {
        return Interactors(getSongs, songPlay)
    }

    fun provideGetSongs(songRepository: SongRepository): GetSongs {
        return GetSongs(songRepository)
    }

    fun providePlaySong(songPlayImpl: IPlaySong): SongPlay {
        return SongPlay(songPlayImpl)
    }

    fun provideSongPlay(context: Context, mediaPlayer: MediaPlayer): MusicPlay {
        return MusicPlay(context, mediaPlayer)
    }
    single { provideInteractors(get(), get()) }
    single { provideGetSongs(get()) }
    single { providePlaySong(get<MusicPlay>()) }
    single { provideSongPlay(androidContext(), get()) }
}