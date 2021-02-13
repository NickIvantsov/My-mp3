package com.gmail.ivantsov.nikolai.my_mp3.framework

import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.my_mp3.framework.model.SongModel

class SongModelDataMapper {

    fun transform(song: Song) = SongModel(
        song.id,
        song.albumId,
        song.artistId,
        song.albumName,
        song.artistName,
        song.duration,
        song.title,
        song.trackNumber
    )

    fun transform(song: SongModel) = Song(
        song.id,
        song.albumId,
        song.artistId,
        song.albumName,
        song.artistName,
        song.duration,
        song.title,
        song.trackNumber
    )

    fun transform(songs: List<Song>): List<SongModel> {
        val resultSongsList = ArrayList<SongModel>()
        songs.forEach { song ->
            resultSongsList.add(transform(song))
        }
        return resultSongsList
    }
}