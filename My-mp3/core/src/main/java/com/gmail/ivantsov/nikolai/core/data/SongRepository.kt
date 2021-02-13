package com.gmail.ivantsov.nikolai.core.data

class SongRepository(private val dataSource: ISongDataSource) : ISongRepository {
    override suspend fun getSongs() = dataSource.readAll()
}