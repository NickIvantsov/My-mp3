package com.gmail.ivantsov.nikolai.my_mp3.framework

import android.content.ContentUris
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.provider.MediaStore
import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.core.domain.IPlaySong
import timber.log.Timber
import java.io.IOException

class MusicPlay(private val context: Context, private val mediaPlayer: MediaPlayer) :
    IPlaySong {
    override fun play(song: Song) {
        playImpl(song)
    }

    private fun playImpl(song: Song) {
        val contentUri: Uri =
            ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song.id)
        if (mediaPlayer.isPlaying)
            mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(context, contentUri)
        } catch (e: IllegalArgumentException) {
            Timber.e(e)
        } catch (e: IllegalStateException) {
            Timber.e(e)
        } catch (e: IOException) {
            Timber.e(e)
        }
        try {
            mediaPlayer.prepare()
        } catch (e: IllegalStateException) {
            Timber.e(e)
        } catch (e: IOException) {
            Timber.e(e)
        }
        mediaPlayer.start()
    }
}