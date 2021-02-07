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
import timber.log.Timber
import java.io.IOException

class MusicPlayer(private val context: Context, private val mediaPlayer: MediaPlayer) :
    IPlaySong, IPauseSong, IResumeSong {
    //region интерфейсы
    override fun play(song: Song) {
        playImpl(song)
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun resume() {
        playSong()
    }
    //endregion
    //region реализация
    private fun playImpl(song: Song) {
        resetSong()
        setDataSource(song)
        prepareSong()
        playSong()
    }

    private fun setDataSource(song: Song) {
        try {
            mediaPlayer.setDataSource(context, getSongUri(song))
        } catch (e: IllegalArgumentException) {
            Timber.e(e)
        } catch (e: IllegalStateException) {
            Timber.e(e)
        } catch (e: IOException) {
            Timber.e(e)
        }
    }

    private fun prepareSong() {
        try {
            mediaPlayer.prepare()
        } catch (e: IllegalStateException) {
            Timber.e(e)
        } catch (e: IOException) {
            Timber.e(e)
        }
    }

    private fun resetSong() {
        mediaPlayer.reset()
    }

    private fun getSongUri(song: Song): Uri =
        ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song.id)

    private fun playSong() {
        mediaPlayer.start()
    }
    //endregion

}