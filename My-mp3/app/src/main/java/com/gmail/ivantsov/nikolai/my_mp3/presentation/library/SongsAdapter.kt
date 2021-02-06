package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.my_mp3.R

class SongsAdapter : RecyclerView.Adapter<SongsViewHolder>() {
    private val songs: MutableList<Song> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View =
            inflater.inflate(R.layout.item_song, parent, false)
        return SongsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val song = songs[position]
        holder.binding.tvSongTitle.text = song.title
        holder.binding.tvSongArtist.text = song.artistName
    }

    override fun getItemCount(): Int = songs.size

    fun add(song: Song) {
        songs.add(song)
        notifyItemInserted(songs.size - 1)
    }

    fun addAll(songList: List<Song>) {
        songs.addAll(songList)
        notifyItemInserted(songs.size - 1)
    }
}