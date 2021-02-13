package com.gmail.ivantsov.nikolai.core.data

class SongRepository(private val dataSource: ISongDataSource) {
    suspend fun getSongs() = dataSource.readAll()
}