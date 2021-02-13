package com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer

import com.gmail.ivantsov.nikolai.my_mp3.framework.model.SongModel

interface IPlaySong {
    fun play(song: SongModel)
}