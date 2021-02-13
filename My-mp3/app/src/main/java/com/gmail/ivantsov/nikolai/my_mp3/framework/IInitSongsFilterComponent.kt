package com.gmail.ivantsov.nikolai.my_mp3.framework

import com.gmail.ivantsov.nikolai.my_mp3.framework.model.SongModel

interface IInitSongsFilterComponent {
    fun init(
        songsFull: List<SongModel>,
        songs: MutableList<SongModel>,
        publishResults: () -> Unit = {}
    )
}