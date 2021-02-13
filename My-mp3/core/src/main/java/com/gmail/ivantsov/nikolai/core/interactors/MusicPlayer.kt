package com.gmail.ivantsov.nikolai.core.interactors

import com.gmail.ivantsov.nikolai.core.data.SongRepository
import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.core.domain.musicPlayer.IMusicPlayer

class MusicPlayer(
    private val songRepository: SongRepository,
    private val musicPlayer: IMusicPlayer
) {
    suspend operator fun invoke() = songRepository.getSongs()

    fun pause() = musicPlayer.pause()

    fun play(song: Song) = musicPlayer.play(song)

    fun resume() = musicPlayer.resume()
}