package com.gmail.ivantsov.nikolai.my_mp3.framework

import com.gmail.ivantsov.nikolai.core.interactors.GetSongs
import com.gmail.ivantsov.nikolai.core.interactors.MusicPlayer

data class Interactors(
    val getSongs: GetSongs,
    val musicPlayer: MusicPlayer
)