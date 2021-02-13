package com.gmail.ivantsov.nikolai.my_mp3.framework

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.text.TextUtils
import com.gmail.ivantsov.nikolai.core.data.ISongDataSource
import com.gmail.ivantsov.nikolai.core.domain.Song


class SongDataSource(private val context: Context) : ISongDataSource {
    companion object {
        private const val ELEMENT_ID_ID = 0
        private const val ELEMENT_TITLE_ID = 1
        private const val ELEMENT_ARTIST_ID = 2
        private const val ELEMENT_ALBUM_ID = 3
        private const val ELEMENT_DURATION_ID = 4
        private const val ELEMENT_TRACK_NUMBER_ID = 5
        private const val ELEMENT_ARTIST_ID_ID = 6
        private const val ELEMENT_ALBUM_ID_ID = 7

        private const val MEDIA_ID = "_id"
        private const val MEDIA_TITLE = "title"
        private const val MEDIA_ARTIST = "artist"
        private const val MEDIA_ALBUM = "album"
        private const val MEDIA_DURATION = "duration"
        private const val MEDIA_TRACK = "track"
        private const val MEDIA_ARTIST_ID = "artist_id"
        private const val MEDIA_ALBUM_ID = "album_id"
    }


    override suspend fun readAll() = getAllSongs(context)

    private fun getAllSongs(context: Context): List<Song> {
        val cursor = makeSongCursor(context)
        return getSongsForCursor(cursor)
    }

    private fun getSongsForCursor(
        cursor: Cursor?
    ): List<Song> {
        val songList = mutableListOf<Song>()
        if (cursor != null && cursor.moveToFirst()) do {

            val id = cursor.getLong(ELEMENT_ID_ID)
            val title = cursor.getString(ELEMENT_TITLE_ID)
            val artist = cursor.getString(ELEMENT_ARTIST_ID)
            val album = cursor.getString(ELEMENT_ALBUM_ID)
            val duration = cursor.getInt(ELEMENT_DURATION_ID)
            val trackNumber = cursor.getInt(ELEMENT_TRACK_NUMBER_ID)
            val artistId = cursor.getInt(ELEMENT_ARTIST_ID_ID).toLong()
            val albumId = cursor.getLong(ELEMENT_ALBUM_ID_ID)

            val song = Song(
                id = id,
                albumId = albumId,
                artistId = artistId,
                title = title,
                artistName = artist,
                albumName = album,
                duration = duration,
                trackNumber = trackNumber
            )
            songList.add(song)

        } while (cursor.moveToNext())
        cursor?.close()
        return songList
    }

    private fun makeSongCursor(
        context: Context,
        paramArrayOfString: Array<String?>? = null,
        selection: String? = null
    ): Cursor? {
        val songSortOrder: String = MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        return makeSongCursor(
            context,
            selection,
            paramArrayOfString,
            songSortOrder
        )
    }

    private fun makeSongCursor(
        context: Context,
        selection: String?,
        paramArrayOfString: Array<String?>?,
        sortOrder: String
    ): Cursor? {
        var selectionStatement = "is_music=1 AND title != ''"
        if (!TextUtils.isEmpty(selection)) {
            selectionStatement = "$selectionStatement AND $selection"
        }
        return context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MEDIA_ID,
                MEDIA_TITLE,
                MEDIA_ARTIST,
                MEDIA_ALBUM,
                MEDIA_DURATION,
                MEDIA_TRACK,
                MEDIA_ARTIST_ID,
                MEDIA_ALBUM_ID
            ),
            selectionStatement,
            paramArrayOfString,
            sortOrder
        )
    }
}