package com.gmail.ivantsov.nikolai.core.data

import com.gmail.ivantsov.nikolai.core.domain.Song

interface ISongDataSource {
    suspend fun readAll(): List<Song>
}