package com.gmail.ivantsov.nikolai.core.data

import com.gmail.ivantsov.nikolai.core.domain.Album
import com.gmail.ivantsov.nikolai.core.domain.Song

class AlbumRepository(private val dataSource: AlbumDataSource) {
    suspend fun addAlbum(album: Album) = dataSource.add(album)

    suspend fun read(album: Album): List<Song> = dataSource.read(album)

    suspend fun remove(album: Album) = dataSource.remove(album)
}