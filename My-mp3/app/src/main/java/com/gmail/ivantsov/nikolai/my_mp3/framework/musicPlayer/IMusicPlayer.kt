package com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer

import android.media.MediaPlayer

interface IMusicPlayer :
    IPauseSong,
    IPlaySong,
    IResumeSong,
    ISongCurrentPosition,
    ISongSeekTo,
    IFileDuration,
    MediaPlayer.OnErrorListener,
    MediaPlayer.OnCompletionListener {
    fun isPlaying(): Boolean
    fun setPlaybackListener(listener: IPlaybackListener)
}