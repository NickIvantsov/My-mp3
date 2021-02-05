package com.gmail.ivantsov.nikolai.core.data

import com.gmail.ivantsov.nikolai.core.domain.Artist
import com.gmail.ivantsov.nikolai.core.domain.Song

class SongRepository(private val dataSource: ISongDataSource) {
    suspend fun addSong(song: Song) =
        dataSource.add(song)

    suspend fun getSongs(artist: Artist) = dataSource.read(artist)

    suspend fun getSongs() = dataSource.readAll()

    suspend fun removeSong(song: Song) =
        dataSource.remove(song)
}