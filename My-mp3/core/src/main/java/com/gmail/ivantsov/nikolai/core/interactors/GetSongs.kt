package com.gmail.ivantsov.nikolai.core.interactors

import com.gmail.ivantsov.nikolai.core.data.SongRepository


class GetSongs(private val songRepository: SongRepository) {
    suspend operator fun invoke() = songRepository.getSongs()
}