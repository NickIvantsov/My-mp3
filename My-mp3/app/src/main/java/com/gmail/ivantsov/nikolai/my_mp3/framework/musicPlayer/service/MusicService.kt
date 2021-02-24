package com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer.IPlaybackListener

class MusicService : Service(), IPlaybackListener {
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun trackWentToNext() {
        TODO("Not yet implemented")
    }

    override fun trackEnded() {
        TODO("Not yet implemented")
    }
}