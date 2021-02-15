package com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer

interface IPlaybackListener {
    fun trackWentToNext()

    fun trackEnded()
}