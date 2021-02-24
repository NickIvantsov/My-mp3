package com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer

import android.media.MediaPlayer
import com.gmail.ivantsov.nikolai.my_mp3.framework.model.SongModel

interface IMusicPlayer :
    IPauseSong,
    IPlaySong,
    IResumeSong,
    ISongCurrentPosition,
    ISongSeekTo,
    IFileDuration,
    MediaPlayer.OnErrorListener,
    MediaPlayer.OnCompletionListener,
    INextSong {
    fun setPlaybackListener(listener: IPlaybackListener)

    fun setDataSource(song: SongModel)

    fun start()

    fun stop()

    fun release()

    fun position(): Int

    fun setVolume(vol: Float)

    fun getAudioSessionId(): Int
    fun isPlaying(): Boolean

}