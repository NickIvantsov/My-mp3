package com.gmail.ivantsov.nikolai.my_mp3.framework

import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.my_mp3.framework.model.SongModel

/**
 * Mapper class used to transform [Song] (in the domain layer) to [SongModel] in the
 * presentation layer.
 */
class SongModelDataMapper {
    /**
     * Transform a [Song] into an [SongModel].
     *
     * @param song Object to be transformed.
     * @return [SongModel].
     */
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

    /**
     * Transform a [SongModel] into an [Song].
     *
     * @param song Object to be transformed.
     * @return [Song].
     */
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

    /**
     * Transform a Collection of [Song] into a Collection of [SongModel].
     *
     * @param songs Objects to be transformed.
     * @return List of [SongModel].
     */
    fun transform(songs: List<Song>): List<SongModel> {
        val resultSongsList = ArrayList<SongModel>()
        songs.forEach { song ->
            resultSongsList.add(transform(song))
        }
        return resultSongsList
    }
}