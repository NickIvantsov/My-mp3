package com.gmail.ivantsov.nikolai.my_mp3.di

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.PowerManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val mediaPlayerModule = module {

    fun provideMediaPlayer(context: Context): MediaPlayer {
        return MediaPlayer().apply {
            setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK)
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
    }
    single { provideMediaPlayer(androidContext()) }
}