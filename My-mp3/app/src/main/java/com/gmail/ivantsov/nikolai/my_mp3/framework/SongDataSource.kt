package com.gmail.ivantsov.nikolai.my_mp3.framework

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
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

    private fun scanDeviceForMp3Files(): List<Song> {
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

        val sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC"
        val mp3Files = ArrayList<Song>()
        var cursor: Cursor? = null
        var selectionStatement = "is_music=1 AND title != ''"

        if (!TextUtils.isEmpty(selection)) {
            selectionStatement = "$selectionStatement AND $selection"
        }
        try {
            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            cursor = context.contentResolver.query(
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
                null,
                sortOrder
            )

//            cursor = context.contentResolver.query(uri, projection, selection, null, sortOrder)
            if (cursor != null) {
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    val id = cursor.getLong(0)
                    val title = cursor.getString(1)
                    val artist = cursor.getString(2)
                    val album = cursor.getString(3)
                    val duration = cursor.getInt(4)
                    val trackNumber = cursor.getInt(5)
                    val artistId = cursor.getInt(6).toLong()
                    val albumId = cursor.getLong(7)
                    cursor.moveToNext()
                    mp3Files.add(
                        Song(
                            id = id,
                            albumId = albumId,
                            artistId = artistId,
                            title = title,
                            artistName = artist,
                            albumName = album,
                            duration = duration,
                            trackNumber = trackNumber
                        )
                    )
                }
            }

            // print to see list of mp3 files
            for (file in mp3Files) {
                Log.i("TAG", file.toString())
            }
        } catch (e: Exception) {
            Log.e("TAG", e.toString())
        } finally {
            cursor?.close()
        }
        return mp3Files
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