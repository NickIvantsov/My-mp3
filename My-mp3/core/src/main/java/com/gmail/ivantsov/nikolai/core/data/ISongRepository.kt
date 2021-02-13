package com.gmail.ivantsov.nikolai.core.data

import com.gmail.ivantsov.nikolai.core.domain.Song

interface ISongRepository {
    suspend fun getSongs(): List<Song>
}