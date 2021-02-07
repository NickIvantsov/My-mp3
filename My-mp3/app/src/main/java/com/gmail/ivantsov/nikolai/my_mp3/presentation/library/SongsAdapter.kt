package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gmail.ivantsov.nikolai.core.domain.INT_NOT_INIT
import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.my_mp3.R


class SongsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //region поля

    lateinit var itemClickListener: (Song) -> Unit
    private val EMPTY_VIEW = 77777
    private val songs: MutableList<Song> = mutableListOf()
    private lateinit var context: Context
    private var songItemPosition = INT_NOT_INIT
    private var songItem: Song? = null

    //endregion
    //region интерфейсы
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        if (viewType == EMPTY_VIEW) {
            val view: View =
                inflater.inflate(R.layout.empty_songs_view, parent, false)
            return EmptySongsViewHolder(view)
        } else {
            val view: View =
                inflater.inflate(R.layout.item_song, parent, false)
            return SongsViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == EMPTY_VIEW) {
            //do nothing
        } else {
            val song = songs[position]
            if (holder is SongsViewHolder) {
                if (song.id == songItem?.id) {
                    setItemColorActive(holder, R.color.purple_200)
                } else {
                    setItemColorNotActive(holder, R.color.black)
                }
                holder.binding.tvSongTitle.text = song.title
                holder.binding.tvSongArtist.text = song.artistName
                holder.binding.songItemContainer.setOnClickListener {
                    songItemPosition = position
                    songItem = song
                    setItemColorActiveAndUpdate(holder, R.color.purple_200)
                    itemClickListener(song)
                }
            }
        }

    }

    override fun getItemCount(): Int = if (songs.size > 0) songs.size else 1

    fun add(song: Song) {
        songs.add(song)
        notifyItemInserted(songs.size - 1)
    }

    fun addAll(songList: List<Song>) {
        songs.addAll(songList)
        notifyItemInserted(songs.size - 1)
    }

    override fun getItemViewType(position: Int): Int {
        if (songs.size == 0) {
            return EMPTY_VIEW
        }
        return super.getItemViewType(position)
    }

    //endregion
    //region реализация
    private fun setItemColorActiveAndUpdate(holder: SongsViewHolder, color: Int) {
        setItemColor(holder, color)
        notifyDataSetChanged()
    }

    private fun setItemColorActive(holder: SongsViewHolder, color: Int) {
        setItemColor(holder, color)
    }

    private fun setItemColorNotActive(holder: SongsViewHolder, color: Int) {
        setItemColor(holder, color)
    }

    private fun setItemColor(holder: SongsViewHolder, color: Int) {
        holder.binding.tvSongTitle.setTextColor(ContextCompat.getColor(context, color))
    }
    //endregion
}