package com.gmail.ivantsov.nikolai.core.interactors

import com.gmail.ivantsov.nikolai.core.domain.IResumeSong

class SongResume(private val songResume: IResumeSong) {
    fun resume() = songResume.resume()
}