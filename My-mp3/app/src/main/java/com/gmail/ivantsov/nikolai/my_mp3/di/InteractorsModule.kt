package com.gmail.ivantsov.nikolai.my_mp3.di

import android.content.Context
import android.media.MediaPlayer
import com.gmail.ivantsov.nikolai.core.data.SongRepository
import com.gmail.ivantsov.nikolai.core.domain.IPauseSong
import com.gmail.ivantsov.nikolai.core.domain.IPlaySong
import com.gmail.ivantsov.nikolai.core.domain.IResumeSong
import com.gmail.ivantsov.nikolai.core.interactors.GetSongs
import com.gmail.ivantsov.nikolai.core.interactors.SongPause
import com.gmail.ivantsov.nikolai.core.interactors.SongPlay
import com.gmail.ivantsov.nikolai.core.interactors.SongResume
import com.gmail.ivantsov.nikolai.my_mp3.framework.Interactors
import com.gmail.ivantsov.nikolai.my_mp3.framework.MusicPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val interactorsModule = module {
    fun provideInteractors(
        getSongs: GetSongs,
        songPlay: SongPlay,
        songPause: SongPause,
        songResume: SongResume
    ): Interactors {
        return Interactors(
            getSongs,
            songPlay,
            songPause,
            songResume
        )
    }

    fun provideGetSongs(songRepository: SongRepository): GetSongs {
        return GetSongs(songRepository)
    }

    fun providePlaySong(songPlayImpl: IPlaySong): SongPlay {
        return SongPlay(songPlayImpl)
    }

    fun providePauseSong(songPauseImpl: IPauseSong): SongPause {
        return SongPause(songPauseImpl)
    }

    fun provideResumeSong(songResumeImpl: IResumeSong): SongResume {
        return SongResume(songResumeImpl)
    }

    fun provideMusicPlay(context: Context, mediaPlayer: MediaPlayer): MusicPlayer {
        return MusicPlayer(context, mediaPlayer)
    }
    single { provideInteractors(get(), get(), get(), get()) }
    single { provideGetSongs(get()) }
    single { providePlaySong(get<MusicPlayer>()) }
    single { providePauseSong(get<MusicPlayer>()) }
    single { provideResumeSong(get<MusicPlayer>()) }
    single { provideMusicPlay(androidContext(), get()) }
}