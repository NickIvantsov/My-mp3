package com.gmail.ivantsov.nikolai.core.domain

class Song(
    val id: Long = LONG_NOT_INIT,
    val albumId: Long = LONG_NOT_INIT,
    val artistId: Long = LONG_NOT_INIT,
    val albumName: String = STRING_NOT_INIT,
    val artistName: String = STRING_NOT_INIT,
    val duration: Int = INT_NOT_INIT,
    val title: String = STRING_NOT_INIT,
    val trackNumber: Int = INT_NOT_INIT

)
