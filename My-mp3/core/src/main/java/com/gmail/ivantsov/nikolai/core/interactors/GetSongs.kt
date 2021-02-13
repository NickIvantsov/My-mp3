package com.gmail.ivantsov.nikolai.core.interactors

import com.gmail.ivantsov.nikolai.core.data.ISongRepository


class GetSongs(private val songRepository: ISongRepository) {
    suspend operator fun invoke() = songRepository.getSongs()
}