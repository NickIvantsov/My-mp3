package com.gmail.ivantsov.nikolai.my_mp3.framework

import android.content.ContentUris
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.provider.MediaStore
import com.gmail.ivantsov.nikolai.core.domain.IPauseSong
import com.gmail.ivantsov.nikolai.core.domain.IPlaySong
import com.gmail.ivantsov.nikolai.core.domain.IResumeSong
import com.gmail.ivantsov.nikolai.core.domain.Song
import java.io.IOException

class MusicPlayer(private val context: Context, private val mediaPlayer: MediaPlayer) :
    IPlaySong, IPauseSong, IResumeSong {
    //region интерфейсы
    @Throws(
        IOException::class,
        IllegalArgumentException::class,
        IllegalStateException::class,
        SecurityException::class
    )
    override fun play(song: Song) {
        playImpl(song)
    }

    @Throws(IllegalStateException::class)
    override fun pause() {
        mediaPlayer.pause()
    }

    override fun resume() {
        playSong()
    }

    //endregion
    //region реализация
    @Throws(
        IOException::class,
        IllegalArgumentException::class,
        IllegalStateException::class,
        SecurityException::class
    )
    private fun playImpl(song: Song) {
        resetSong()
        setDataSource(song)
        prepareSong()
        playSong()
    }

    @Throws(
        IOException::class,
        IllegalArgumentException::class,
        IllegalStateException::class,
        SecurityException::class
    )
    private fun setDataSource(song: Song) {
        mediaPlayer.setDataSource(context, getSongUri(song))
    }

    @Throws(IOException::class, IllegalStateException::class)
    private fun prepareSong() {
        mediaPlayer.prepare()
    }

    private fun resetSong() {
        mediaPlayer.reset()
    }

    private fun getSongUri(song: Song): Uri =
        ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song.id)

    @Throws(IllegalStateException::class)
    private fun playSong() {
        mediaPlayer.start()
    }
    //endregion

}