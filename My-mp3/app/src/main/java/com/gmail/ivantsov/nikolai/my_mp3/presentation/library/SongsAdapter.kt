package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.ivantsov.nikolai.core.domain.INT_NOT_INIT
import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.my_mp3.R
import com.gmail.ivantsov.nikolai.my_mp3.framework.IInitSongsFilterComponent


class SongsAdapter(
    private val songsFilter: Filter,
    initSongsFilterComponent: IInitSongsFilterComponent
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    //region поля

    companion object {
        private const val EMPTY_VIEW = 77777
        private const val URL_TO_ALBUM_ART = "content://media/external/audio/albumart"
        private const val LIST_EMPTY = 0
    }

    lateinit var itemClickListener: (Song) -> Unit
    private val songs: MutableList<Song> = mutableListOf()
    private val songsFull: MutableList<Song> = mutableListOf()
    private lateinit var context: Context
    private var songItemPosition = INT_NOT_INIT
    private var songItem: Song? = null
    private val itemOnClickListener =
        { _: View, holder: SongsViewHolder, position: Int, song: Song ->
            songItemPosition = position
            songItem = song
            setItemColorActiveAndUpdate(holder, R.color.purple_200)
            itemClickListener(song)
        }

    //endregion
    init {
        initSongsFilterComponent.init(songsFull, songs) {
            notifyDataSetChanged()
        }
    }

    //region интерфейсы
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        return viewHolderFor(viewType, inflater, parent)

    }

    private fun viewHolderFor(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ) = if (viewType == EMPTY_VIEW) {
        EmptySongsViewHolder(
            getView(
                inflater,
                parent,
                R.layout.empty_songs_view
            )
        )
    } else {
        SongsViewHolder(
            getView(
                inflater,
                parent,
                R.layout.item_song
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) != EMPTY_VIEW) {
            val song = songs[position]
            if (holder is SongsViewHolder) {
                setItemColor(song, holder)
                holder.binding.tvSongTitle.text = song.title
                holder.binding.tvSongArtist.text = song.artistName
                setAlbumArt(song, holder)
                holder.binding.songItemContainer.setOnClickListener { view ->
                    itemOnClickListener(
                        view,
                        holder,
                        position,
                        song
                    )
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
        songsFull.addAll(songList)
        if (songList.size == 1)
            notifyItemInserted(songs.size)
        else {
            notifyItemInserted(songs.size - 1)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (songs.size == LIST_EMPTY) {
            return EMPTY_VIEW
        }
        return super.getItemViewType(position)
    }


    override fun getFilter(): Filter = songsFilter

    //endregion
    //region реализация
    private fun getView(
        inflater: LayoutInflater,
        parent: ViewGroup,
        layoutId: Int
    ): View {
        return inflater.inflate(layoutId, parent, false)
    }

    private fun setItemColor(
        song: Song,
        holder: SongsViewHolder
    ) {
        if (song.id == songItem?.id) {
            setItemColorActive(holder, R.color.purple_200)
        } else {
            setItemColorNotActive(holder, R.color.black)
        }
    }

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

    private fun getAlbumArtUri(albumId: Long): Uri {
        return ContentUris.withAppendedId(
            Uri.parse(URL_TO_ALBUM_ART),
            albumId
        )
    }

    private fun setAlbumArt(
        song: Song,
        holder: SongsViewHolder
    ) {
        Glide.with(context)
            .load(getAlbumArtUri(song.albumId))
            .placeholder(R.drawable.ic_baseline_headset_24)
            .into(holder.binding.imageView3);
    }

    //endregion
}