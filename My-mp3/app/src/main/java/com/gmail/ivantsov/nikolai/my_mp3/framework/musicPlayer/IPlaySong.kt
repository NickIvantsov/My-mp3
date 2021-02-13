package com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer

import com.gmail.ivantsov.nikolai.core.domain.Song

interface IPlaySong {
    fun play(song: Song)
}