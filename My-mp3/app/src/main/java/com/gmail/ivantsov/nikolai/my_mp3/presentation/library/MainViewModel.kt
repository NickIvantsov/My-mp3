package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.ivantsov.nikolai.core.domain.LONG_NOT_INIT
import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.my_mp3.framework.Interactors
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class MainViewModel(
    application: Application,
    private val interactors: Interactors
) : AndroidViewModel(application) {
    //region поля
    private var isPlaying = false
    private var isPause = false
    private var songId = LONG_NOT_INIT
    private val songs: MutableLiveData<List<Song>> = MutableLiveData()
    private val error: MutableLiveData<String> = MutableLiveData()

    //endregion
    //region интерфейсы
    fun loadSongs() {
        viewModelScope.launch {
            songs.postValue(interactors.getSongs())
        }
    }

    fun getSongsLiveData(): LiveData<List<Song>> = songs
    fun getErrorLiveData(): LiveData<String> = error

    fun playSong(song: Song) {
        if (songId == song.id) {
            if (isPlaying) {
                try {
                    pause()
                } catch (ex: IllegalStateException) {
                    error.value = "Внутренний движок плеера не был инициализирован." //todo хардкод
                    Timber.e(ex)
                }
            } else if (isPause) {
                try {
                    resume()
                } catch (ex: Throwable) {
                    error.value = ex.message
                    Timber.e(ex)
                }
            }
        } else {
            try {
                play(song)
            } catch (ex: IllegalArgumentException) {
                error.value = ex.message
                Timber.e(ex)
            } catch (ex: IllegalStateException) {
                error.value = "Вызван в недопустимом состоянии." //todo хардкод
                Timber.e(ex)
            } catch (ex: SecurityException) {
                error.value = "нарушение безопасности ${ex.message}" //todo хардкод
                Timber.e(ex)
            } catch (ex: IOException) {
                error.value = "Произошло какое-то исключение ввода-вывода." //todo хардкод
                Timber.e(ex)
            }
        }
    }

    //endregion
    //region реализация
    private fun play(song: Song) {
        interactors.songPlay.play(song)
        songId = song.id
        isPlaying = true
    }

    private fun resume() {
        interactors.songResume.resume()
        isPlaying = true
        isPause = false
    }

    private fun pause() {
        interactors.songPause.pause()
        isPause = true
        isPlaying = false
    }
    //endregion
}