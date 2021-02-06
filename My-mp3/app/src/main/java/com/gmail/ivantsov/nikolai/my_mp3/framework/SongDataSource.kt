package com.gmail.ivantsov.nikolai.my_mp3.framework

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.text.TextUtils
import com.gmail.ivantsov.nikolai.core.data.ISongDataSource
import com.gmail.ivantsov.nikolai.core.domain.Artist
import com.gmail.ivantsov.nikolai.core.domain.Song


class SongDataSource(private val context: Context) : ISongDataSource {
    override suspend fun add(song: Song) {
        TODO("Not yet implemented")
    }

    override suspend fun read(artist: Artist): List<Song> {
        TODO("Not yet implemented")
    }

    override suspend fun readAll() = getAllSongs(context)
    override suspend fun remove(song: Song) {
        TODO("Not yet implemented")
    }


    fun getAllSongs(context: Context): ArrayList<Song> {
        return getSongsForCursor(
            makeSongCursor(
                context,
                null,
                null
            )
        )
    }
    fun getSongsForCursor(cursor: Cursor?): ArrayList<Song> {
        val arrayList = ArrayList<Song>()
        if (cursor != null && cursor.moveToFirst()) do {
            val id = cursor.getLong(0)
            val title = cursor.getString(1)
            val artist = cursor.getString(2)
            val album = cursor.getString(3)
            val duration = cursor.getInt(4)
            val trackNumber = cursor.getInt(5)
            val artistId = cursor.getInt(6).toLong()
            val albumId = cursor.getLong(7)
            arrayList.add(Song( id = id,
                albumId = albumId,
                artistId = artistId,
                title = title,
                artistName = artist,
                albumName = album,
                duration = duration,
                trackNumber = trackNumber))
        } while (cursor.moveToNext())
        cursor?.close()
        return arrayList
    }
    fun makeSongCursor(
        context: Context,
        selection: String?,
        paramArrayOfString: Array<String?>?
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
                "_id",
                "title",
                "artist",
                "album",
                "duration",
                "track",
                "artist_id",
                "album_id"
            ),
            selectionStatement,
            paramArrayOfString,
            sortOrder
        )
    }
}