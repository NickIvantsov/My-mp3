package com.gmail.ivantsov.nikolai.my_mp3.framework

import com.gmail.ivantsov.nikolai.core.interactors.GetSongs
import com.gmail.ivantsov.nikolai.core.interactors.SongPause
import com.gmail.ivantsov.nikolai.core.interactors.SongPlay

data class Interactors(
    val getSongs: GetSongs,
    val songPlay: SongPlay,
    val songPause: SongPause
)