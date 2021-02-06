package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.my_mp3.framework.Interactors
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val interactors: Interactors
) : AndroidViewModel(application) {

    private val songs: MutableLiveData<List<Song>> = MutableLiveData()
    fun loadSongs() {
        viewModelScope.launch {
            songs.postValue(interactors.getSongs())
        }
    }

    fun getSongsLiveData(): LiveData<List<Song>> = songs

    fun playSong(song: Song) {
        interactors.songPlay.play(song)
    }

}