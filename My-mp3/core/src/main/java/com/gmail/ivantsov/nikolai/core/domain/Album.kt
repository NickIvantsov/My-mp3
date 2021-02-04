package com.gmail.ivantsov.nikolai.core.domain


data class Album(
    val id: Long = LONG_NOT_INIT,
    val artistId: Long = LONG_NOT_INIT,
    val artistName: String,
    val songCount: Int = INT_NOT_INIT,
    val title: String = STRING_NOT_INIT,
    val year: Int = INT_NOT_INIT
)
