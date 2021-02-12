package com.gmail.ivantsov.nikolai.core.interactors

import com.gmail.ivantsov.nikolai.core.domain.IPauseSong

class SongPause(private val pauseSong: IPauseSong) {
    fun pause() = pauseSong.pause()
}