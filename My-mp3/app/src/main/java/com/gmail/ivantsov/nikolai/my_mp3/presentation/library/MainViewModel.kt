package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.ivantsov.nikolai.my_mp3.R
import com.gmail.ivantsov.nikolai.my_mp3.framework.Interactors
import com.gmail.ivantsov.nikolai.my_mp3.framework.LONG_NOT_INIT
import com.gmail.ivantsov.nikolai.my_mp3.framework.SongModelDataMapper
import com.gmail.ivantsov.nikolai.my_mp3.framework.model.SongModel
import com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer.IMusicPlayer
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    application: Application,
    private val interactors: Interactors,
    private val songModelDataMapper: SongModelDataMapper,
    private val musicPlayer: IMusicPlayer
) : AndroidViewModel(application) {
    //region поля
    private var isPlaying = false
    private var isPause = false
    private var songId = LONG_NOT_INIT
    private val songs: MutableLiveData<List<SongModel>> = MutableLiveData()
    private val error: MutableLiveData<Int> = MutableLiveData()

    //endregion
    //region интерфейсы
    fun loadSongs() {
        loadSongsImpl()
    }

    fun getSongsLiveData(): LiveData<List<SongModel>> = songs
    fun getErrorLiveData(): LiveData<Int> = error

    fun playSong(song: SongModel) {
        playSongImpl(song)
    }

    //endregion
    //region реализация
    private fun play(song: SongModel) {
        songId = song.id
        musicPlayer.play(song)
        isPlaying = true
    }

    private fun resume() {
        musicPlayer.resume()
        isPlaying = true
        isPause = false
    }

    private fun pause() {
        musicPlayer.pause()
        isPause = true
        isPlaying = false
    }

    private fun loadSongsImpl() {
        viewModelScope.launch {
            songs.postValue(songModelDataMapper.transform(interactors.getSongs()))
        }
    }

    private fun playSongImpl(song: SongModel) {
        try {
            if (songId == song.id) {
                if (isPlaying) {
                    pause()
                } else if (isPause) {
                    resume()
                }
            } else {
                play(song)
            }
        } catch (ex: Throwable) {
            isPlaying = false
            isPause = false
            error.value = R.string.some_unknown_error_media_player
            Timber.e(ex)
        }
    }
    //endregion
}