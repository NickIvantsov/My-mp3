package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.my_mp3.R
import com.gmail.ivantsov.nikolai.my_mp3.framework.IInitSongsFilterComponent


class SongsAdapter(
    private val songsFilter: Filter,
    initSongsFilterComponent: IInitSongsFilterComponent? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {
    //region поля

    companion object {
        private const val EMPTY_VIEW = 77777
        private const val URL_TO_ALBUM_ART = "content://media/external/audio/albumart"
        private const val LIST_EMPTY = 0

        /**
         * список имеет только один элемент (примечание вероятно этот элемент содержит не песню, а
         * является сигналом для
         */
        private const val LIST_HAVE_ONE_ELEMENT = 1
    }

    /**
     * обработкич щелчка по элементу списка по-умолчанию невыполняет никакой работы
     */
    var itemClickListener: (Song) -> Unit = {}

    /**
     * поле по умолчанию установлено в true что гласит о том что необходимо отобразить визуалайзер
     * в случае ошибки в момент воспроизведения или еще по какой-то причине необходимо установить в
     * состояние false для скрытия визуализации проигрывания
     */
    var isNeedViewVisualizer = true

    private val songs: MutableList<Song> = mutableListOf()
    private val songsFull: MutableList<Song> = mutableListOf()

    private var songItem: Song? = null
    private var isPlaying = false
    private var isNewElement = true

    private val itemOnClickListener =
        { _: View, holder: SongsViewHolder, context: Context, song: Song ->
            songItem = song
            setItemColorActiveAndUpdate(holder, R.color.purple_200, context)
            itemClickListener(song)
        }

    //endregion
    //region интерфейсы
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return onCreateViewHolderImpl(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindViewHolderImpl(position, holder)
    }

    override fun getItemCount(): Int = getItemCountImpl()

    /**
     * добавления списка новых элементов при добавлении очищает предведущие значения которые были
     * добавлены ранее
     * @param songList спосок [Song] элементов
     */
    fun addAll(songList: List<Song>) {
        addAllImpl(songList)
    }

    /**
     * добавляет один элемент в имеющийся список
     * @param song аудио файл
     */
    fun add(song: Song) {
        addImpl(song)
    }


    override fun getItemViewType(position: Int): Int {
        return getViewImpl(position)
    }

    override fun getFilter(): Filter = songsFilter
    //endregion
    //region реализация
    private fun addImpl(song: Song) {
        songs.add(song)
        songsFull.add(song)
        notifyItemInserted(songs.size - 1)
    }

    private fun addAllImpl(songList: List<Song>) {
        songs.clear()
        songsFull.clear()
        songs.addAll(songList)
        songsFull.addAll(songList)
        updateAfterAdd(songList)
    }

    private fun getViewImpl(position: Int): Int {
        if (songs.size == LIST_EMPTY) {
            return EMPTY_VIEW
        }
        return super.getItemViewType(position)
    }

    private fun getItemCountImpl() = if (songs.size > 0) songs.size else LIST_HAVE_ONE_ELEMENT

    private fun onCreateViewHolderImpl(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return viewHolderFor(viewType, inflater, parent)
    }

    private fun getView(
        inflater: LayoutInflater,
        parent: ViewGroup,
        layoutId: Int
    ): View {
        return inflater.inflate(layoutId, parent, false)
    }

    private fun setItemColor(
        song: Song,
        holder: SongsViewHolder,
        context: Context
    ) {
        if (song.id == songItem?.id) {
            setItemColorActive(holder, R.color.purple_200, context)
        } else {
            setItemColorNotActive(holder, R.color.black, context)
        }
    }

    private fun setItemColorActiveAndUpdate(holder: SongsViewHolder, color: Int, context: Context) {
        setItemColor(holder, color, context)
        notifyDataSetChanged()
    }

    private fun setItemColorActive(holder: SongsViewHolder, color: Int, context: Context) {
        setItemColor(holder, color, context)
    }

    private fun setItemColorNotActive(holder: SongsViewHolder, color: Int, context: Context) {
        setItemColor(holder, color, context)
    }

    private fun setItemColor(holder: SongsViewHolder, color: Int, context: Context) {
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
        holder: SongsViewHolder,
        context: Context
    ) {
        Glide.with(context)
            .load(getAlbumArtUri(song.albumId))
            .placeholder(R.drawable.ic_baseline_headset_24)
            .into(holder.binding.imageView3);
    }


    private fun onBindViewHolderImpl(
        position: Int,
        holder: RecyclerView.ViewHolder
    ) {
        if (getItemViewType(position) != EMPTY_VIEW) {
            val song = songs[position]
            if (holder is SongsViewHolder) {

                setItemColor(song, holder, holder.binding.root.context)
                holder.binding.tvSongTitle.text = song.title
                holder.binding.tvSongArtist.text = song.artistName
                setAlbumArt(song, holder, holder.binding.root.context)

                holder.binding.songItemContainer.setOnClickListener { view ->
                    songOnClick(song, view, holder)
                }

                if (songItem?.id == song.id) {
                    holder.binding.visualizer.setColor(
                        ContextCompat.getColor(
                            holder.binding.root.context,
                            R.color.purple_200
                        )
                    )
                    if (isPlaying) {
                        setVisualizerVisibility(holder, View.VISIBLE)
                    } else {
                        setVisualizerVisibility(holder, View.GONE)
                    }
                } else {
                    setVisualizerVisibility(holder, View.GONE)
                }

            }
        }
    }

    private fun songOnClick(
        song: Song,
        view: View,
        holder: SongsViewHolder
    ) {
        isNewElement = if (songItem?.id != song.id) {
            isPlaying = false
            isNeedViewVisualizer = true
            true
        } else {
            false
        }
        itemOnClickListener(
            view,
            holder,
            holder.binding.root.context,
            song
        )
        if (isNeedViewVisualizer) {
            isPlaying = needViewVisualizer(holder)
        }
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

    private fun needViewVisualizer(holder: SongsViewHolder) =
        if (isPlaying) {
            setVisualizerVisibility(holder, View.GONE)
            false
        } else {
            setVisualizerVisibility(holder, View.VISIBLE)
            true
        }

    private fun setVisualizerVisibility(holder: SongsViewHolder, visibility: Int) {
        holder.binding.visualizer.visibility = visibility
    }


    private fun updateAfterAdd(songList: List<Song>) {
        if (songList.size == LIST_HAVE_ONE_ELEMENT)
            notifyDataSetChanged()
        else {
            notifyItemInserted(songs.size - 1)
        }
    }

    //endregion

    init {
        initSongsFilterComponent?.init(songsFull, songs) {
            notifyDataSetChanged()
        }
    }
}