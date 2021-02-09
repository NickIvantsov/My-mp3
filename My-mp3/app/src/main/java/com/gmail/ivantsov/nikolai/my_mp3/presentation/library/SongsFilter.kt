package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

import android.widget.Filter
import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.my_mp3.framework.IInitSongsFilterComponent
import java.util.*
import kotlin.collections.ArrayList

/**
 * [songsFull] список всех элементов по которым мы будем производить поиск
 * [songs] список всех элементов которые будут отображатся в результате поиска
 * [publishResultsListener] лямбда выражение которое служит дополнительной работой которую необходимо
 * выполнить после того как мы заполнели список который необходимо отобразить. К примеру вызвать
 * метод обновления списка
 */
class SongsFilter : Filter(), IInitSongsFilterComponent {
    private lateinit var songsFull: List<Song>
    private lateinit var songs: MutableList<Song>
    private lateinit var publishResultsListener: () -> Unit
    override fun init(
        songsFull: List<Song>,
        songs: MutableList<Song>,
        publishResults: () -> Unit
    ) {
        this.songsFull = songsFull
        this.songs = songs
        this.publishResultsListener = publishResults
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val filteredList = ArrayList<Song>()
        if (constraint == null || constraint.isEmpty()) {
            filteredList.addAll(songsFull)
        } else {
            val filterPattern: String = constraint.toString().toLowerCase(Locale.ROOT).trim()
            for (item in songsFull) {
                if (item.title.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                    filteredList.add(item)
                }
            }
        }
        val results = FilterResults()
        results.values = filteredList
        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        songs.clear()
        val res = (results?.values as? ArrayList<Song>)
        if (res != null) {
            songs.addAll(res)
        } else {
            //todo
        }
        publishResultsListener()
    }
}