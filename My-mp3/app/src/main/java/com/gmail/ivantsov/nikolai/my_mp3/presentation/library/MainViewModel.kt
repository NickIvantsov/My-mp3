package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

import android.app.Application
import android.content.ContentUris
import android.media.MediaPlayer
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.my_mp3.framework.Interactors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class MainViewModel(
    application: Application,
    private val interactors: Interactors,
    private val mediaPlayer: MediaPlayer
) :
    AndroidViewModel(application) {

    private val songs: MutableLiveData<List<Song>> = MutableLiveData()
    fun loadSongs() {
        GlobalScope.launch {
            songs.postValue(interactors.getSongs())
        }
    }

    fun getSongsLiveData(): LiveData<List<Song>> = songs

    fun playSong(song: Song) {
        val contentUri: Uri =
            ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song.id)
        if (mediaPlayer.isPlaying)
            mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(getApplication(), contentUri)
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