package com.gmail.ivantsov.nikolai.my_mp3.framework.model

import com.gmail.ivantsov.nikolai.my_mp3.framework.INT_NOT_INIT
import com.gmail.ivantsov.nikolai.my_mp3.framework.LONG_NOT_INIT
import com.gmail.ivantsov.nikolai.my_mp3.framework.STRING_NOT_INIT


data class SongModel(
    val id: Long = LONG_NOT_INIT,
    val albumId: Long = LONG_NOT_INIT,
    val artistId: Long = LONG_NOT_INIT,
    val albumName: String = STRING_NOT_INIT,
    val artistName: String = STRING_NOT_INIT,
    val duration: Int = INT_NOT_INIT,
    val title: String = STRING_NOT_INIT,
    val trackNumber: Int = INT_NOT_INIT
)