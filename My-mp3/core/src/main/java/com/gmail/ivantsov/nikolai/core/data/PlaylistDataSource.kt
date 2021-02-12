package com.gmail.ivantsov.nikolai.core.data

import com.gmail.ivantsov.nikolai.core.domain.Playlist
import com.gmail.ivantsov.nikolai.core.domain.Song

interface PlaylistDataSource {
    suspend fun add(playlist: Playlist)

    suspend fun read(playlist: Playlist): List<Song>

    suspend fun remove(playlist: Playlist)
}