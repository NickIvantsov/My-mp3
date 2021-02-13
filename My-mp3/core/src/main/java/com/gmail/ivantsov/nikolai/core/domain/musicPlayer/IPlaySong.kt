package com.gmail.ivantsov.nikolai.core.domain.musicPlayer

import com.gmail.ivantsov.nikolai.core.domain.Song

interface IPlaySong {
    fun play(song: Song)
}