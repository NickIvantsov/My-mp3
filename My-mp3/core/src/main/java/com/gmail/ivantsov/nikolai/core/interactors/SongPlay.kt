package com.gmail.ivantsov.nikolai.core.interactors

import com.gmail.ivantsov.nikolai.core.domain.IPlaySong
import com.gmail.ivantsov.nikolai.core.domain.Song

class SongPlay(private val songPlay: IPlaySong) {
    fun play(song: Song) = songPlay.play(song)
}