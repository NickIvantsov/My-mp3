package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gmail.ivantsov.nikolai.my_mp3.databinding.ItemSongBinding

class SongsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: ItemSongBinding = ItemSongBinding.bind(itemView)
}