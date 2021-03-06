package com.gmail.ivantsov.nikolai.core.data

import com.gmail.ivantsov.nikolai.core.domain.Artist
import com.gmail.ivantsov.nikolai.core.domain.Song

interface ISongDataSource {
    suspend fun add(song: Song)

    suspend fun read(artist: Artist): List<Song>

    suspend fun readAll(): List<Song>

    suspend fun remove(song: Song)
}