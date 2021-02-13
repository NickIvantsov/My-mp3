package com.gmail.ivantsov.nikolai.my_mp3.framework

import android.content.ContentUris
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.provider.MediaStore
import com.gmail.ivantsov.nikolai.core.domain.musicPlayer.IPauseSong
import com.gmail.ivantsov.nikolai.core.domain.musicPlayer.IPlaySong
import com.gmail.ivantsov.nikolai.core.domain.musicPlayer.IResumeSong
import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.core.domain.musicPlayer.IMusicPlayer
import java.io.IOException

class MusicPlayerImpl(
    private val context: Context,
    private val mediaPlayer: MediaPlayer) : IMusicPlayer {
    //region интерфейсы
    @Throws(
        IOException::class,
        IllegalArgumentException::class,
        IllegalStateException::class,
        SecurityException::class
    )
    override fun play(song: Song) {
        playImpl(song, mediaPlayer, context)
    }

    @Throws(IllegalStateException::class)
    override fun pause() {
        pauseImpl(mediaPlayer)
    }

    private fun pauseImpl(mediaPlayer: MediaPlayer) {
        mediaPlayer.pause()
    }

    override fun resume() {
        resumeSong(mediaPlayer)
    }

    //endregion
    //region реализация
    @Throws(
        IOException::class,
        IllegalArgumentException::class,
        IllegalStateException::class,
        SecurityException::class
    )
    private fun playImpl(song: Song, mediaPlayer: MediaPlayer, context: Context) {
        resetSong(mediaPlayer)
        setDataSource(song, mediaPlayer, context)
        prepareSong(mediaPlayer)
        playSong(mediaPlayer)
    }

    @Throws(
        IOException::class,
        IllegalArgumentException::class,
        IllegalStateException::class,
        SecurityException::class
    )
    private fun setDataSource(song: Song, mediaPlayer: MediaPlayer, context: Context) {
        mediaPlayer.setDataSource(context, getSongUri(song))
    }

    @Throws(IOException::class, IllegalStateException::class)
    private fun prepareSong(mediaPlayer: MediaPlayer) {
        mediaPlayer.prepare()
    }

    private fun resetSong(mediaPlayer: MediaPlayer) {
        mediaPlayer.reset()
    }

    private fun getSongUri(song: Song): Uri =
        ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song.id)

    @Throws(IllegalStateException::class)
    private fun playSong(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()
    }

    @Throws(IllegalStateException::class)
    private fun resumeSong(mediaPlayer: MediaPlayer) {
        playSong(mediaPlayer)
    }
    //endregion

}