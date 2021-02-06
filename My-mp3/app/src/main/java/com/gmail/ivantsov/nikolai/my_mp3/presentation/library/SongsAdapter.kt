package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.my_mp3.R


class SongsAdapter : RecyclerView.Adapter<SongsViewHolder>() {
    private val songs: MutableList<Song> = mutableListOf()
    lateinit var itemClickListener: (Song) -> Unit
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view: View =
            inflater.inflate(R.layout.item_song, parent, false)
        return SongsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val song = songs[position]
        holder.binding.tvSongTitle.text = song.title
        holder.binding.tvSongArtist.text = song.artistName
        holder.binding.songItemContainer.setOnClickListener { itemClickListener(song) }
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