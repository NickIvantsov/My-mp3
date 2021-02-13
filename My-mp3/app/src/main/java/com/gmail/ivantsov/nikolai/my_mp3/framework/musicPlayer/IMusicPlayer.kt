package com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer

import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnErrorListener

interface IMusicPlayer :
    IPauseSong,
    IPlaySong,
    IResumeSong,
    ISongCurrentPosition,
    ISongSeekTo,
    IFileDuration,
    OnErrorListener,
    OnCompletionListener {
    fun isPlaying(): Boolean
}