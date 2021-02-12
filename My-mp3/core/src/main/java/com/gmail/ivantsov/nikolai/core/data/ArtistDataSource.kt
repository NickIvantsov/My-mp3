package com.gmail.ivantsov.nikolai.core.data

import com.gmail.ivantsov.nikolai.core.domain.Album
import com.gmail.ivantsov.nikolai.core.domain.Artist

interface ArtistDataSource {

    suspend fun add(artist: Artist)

    suspend fun read(artist: Artist): List<Album>

    suspend fun remove(artist: Artist)
}