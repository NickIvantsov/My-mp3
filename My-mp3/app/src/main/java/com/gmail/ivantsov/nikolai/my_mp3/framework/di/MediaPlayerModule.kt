package com.gmail.ivantsov.nikolai.my_mp3.framework.di

import android.media.AudioAttributes
import android.media.MediaPlayer
import org.koin.dsl.module.module

val mediaPlayerModule = module {

    fun provideMediaPlayer(): MediaPlayer {
        return MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
    }
    single { provideMediaPlayer() }
}