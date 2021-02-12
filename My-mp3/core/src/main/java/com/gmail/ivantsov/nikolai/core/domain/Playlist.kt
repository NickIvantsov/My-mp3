package com.gmail.ivantsov.nikolai.core.domain

data class Playlist(
    val id: Long = LONG_NOT_INIT,
    val name: String = STRING_NOT_INIT,
    val songCount: Int = INT_NOT_INIT
)
