package com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer.impl

import android.content.ContentUris
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.provider.MediaStore
import com.gmail.ivantsov.nikolai.my_mp3.framework.model.SongModel
import com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer.IMusicPlayer
import java.io.IOException

class MusicPlayerImpl(
    private val context: Context,
    actualMediaPlayer: MediaPlayer
) : IMusicPlayer {

    private var actualMediaPlayer: MediaPlayer = actualMediaPlayer
    private lateinit var nextMediaPlayer: MediaPlayer

    //region интерфейсы
    @Throws(
        IOException::class,
        IllegalArgumentException::class,
        IllegalStateException::class,
        SecurityException::class
    )
    override fun play(song: SongModel) {
        playImpl(song, actualMediaPlayer, context)
    }

    override fun isPlaying() = isPlayingImpl()

    @Throws(IllegalStateException::class)
    override fun pause() {
        pauseImpl(actualMediaPlayer)
    }

    private fun pauseImpl(mediaPlayer: MediaPlayer) {
        mediaPlayer.pause()
    }

    override fun resume() {
        resumeSong(actualMediaPlayer)
    }

    override fun getCurrentPosition() = getCurrentPositionImpl()
    override fun seekTo(timeMs: Int) = seekToImpl(timeMs)
    override fun getDuration() = getDurationImpl()

    override fun onError(mediaPlayer: MediaPlayer?, what: Int, extra: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        TODO("Not yet implemented")
    }

    private fun getDurationImpl() = actualMediaPlayer.duration

    //endregion
    //region реализация
    private fun isPlayingImpl() = actualMediaPlayer.isPlaying
    private fun seekToImpl(timeMs: Int) {
        actualMediaPlayer.seekTo(timeMs)
    }

    private fun getCurrentPositionImpl() = actualMediaPlayer.currentPosition

    @Throws(
        IOException::class,
        IllegalArgumentException::class,
        IllegalStateException::class,
        SecurityException::class
    )
    private fun playImpl(song: SongModel, mediaPlayer: MediaPlayer, context: Context) {
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
    private fun setDataSource(song: SongModel, mediaPlayer: MediaPlayer, context: Context) {
        mediaPlayer.setDataSource(context, getSongUri(song))
    }

    @Throws(IOException::class, IllegalStateException::class)
    private fun prepareSong(mediaPlayer: MediaPlayer) {
        mediaPlayer.prepare()
    }

    private fun resetSong(mediaPlayer: MediaPlayer) {
        mediaPlayer.reset()
    }

    private fun getSongUri(song: SongModel): Uri =
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