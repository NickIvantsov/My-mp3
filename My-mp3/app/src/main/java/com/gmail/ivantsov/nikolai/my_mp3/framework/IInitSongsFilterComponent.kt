package com.gmail.ivantsov.nikolai.my_mp3.framework

import com.gmail.ivantsov.nikolai.core.domain.Song

interface IInitSongsFilterComponent {
    fun init(songsFull: List<Song>, songs: MutableList<Song>, publishResults: () -> Unit = {})
}