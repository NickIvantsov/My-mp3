package com.gmail.ivantsov.nikolai.core.data

import com.gmail.ivantsov.nikolai.core.domain.Album
import com.gmail.ivantsov.nikolai.core.domain.Song

interface AlbumDataSource {

    suspend fun add(album: Album)

    suspend fun read(album: Album): List<Song>

    suspend fun remove(album: Album)
}