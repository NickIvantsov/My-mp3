package com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer.impl

import android.content.ContentUris
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.PowerManager
import android.provider.MediaStore
import com.gmail.ivantsov.nikolai.my_mp3.framework.model.SongModel
import com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer.IMusicPlayer
import com.gmail.ivantsov.nikolai.my_mp3.framework.musicPlayer.IPlaybackListener
import java.io.IOException

class MusicPlayerImpl(
    private val context: Context,
    private var actualMediaPlayer: MediaPlayer,
    private val onErrorListener: MediaPlayer.OnErrorListener,
    private val onCompletionListener: MediaPlayer.OnCompletionListener
) : IMusicPlayer {
    //region поля
    private var nextMediaPlayer: MediaPlayer? = null
    private val audioAttributes = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .build()
    private var playbackListener: IPlaybackListener? = null

    //endregion
    //region интерфейсы
    @Throws(
        IOException::class,
        IllegalArgumentException::class,
        IllegalStateException::class,
        SecurityException::class
    )
    override fun play(song: SongModel) {
        playImpl(
            song,
            actualMediaPlayer,
            context,
            audioAttributes,
            onCompletionListener,
            onErrorListener
        )
    }

    override fun isPlaying() = isPlayingImpl()
    override fun setPlaybackListener(listener: IPlaybackListener) {
        playbackListener = listener
    }

    @Throws(IllegalStateException::class)
    override fun pause() {
        pauseImpl(actualMediaPlayer)
    }

    private fun pauseImpl(mediaPlayer: MediaPlayer) {
        mediaPlayer.pause()
    }

    override fun resume() {
        resumeSong(actualMediaPlayer)
    }

    override fun getCurrentPosition() = getCurrentPositionImpl()
    override fun seekTo(timeMs: Int) = seekToImpl(timeMs)
    override fun getDuration() = getDurationImpl()
    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        actualMediaPlayer.release()
        actualMediaPlayer = MediaPlayer().apply {
            setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK)
        }
        return false
    }

    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        val nextMediaPlayer = this.nextMediaPlayer
        if (mediaPlayer == actualMediaPlayer && nextMediaPlayer != null) {
            actualMediaPlayer.release()
            actualMediaPlayer = nextMediaPlayer
            this.nextMediaPlayer = null
            playbackListener?.trackWentToNext()
        } else {
            playbackListener?.trackEnded()
        }
    }


    //endregion
    //region реализация

    private fun getDurationImpl() = actualMediaPlayer.duration
    private fun isPlayingImpl() = actualMediaPlayer.isPlaying
    private fun seekToImpl(timeMs: Int) {
        actualMediaPlayer.seekTo(timeMs)
    }

    private fun getCurrentPositionImpl() = actualMediaPlayer.currentPosition

    @Throws(
        IOException::class,
        IllegalArgumentException::class,
        IllegalStateException::class,
        SecurityException::class
    )
    private fun playImpl(
        song: SongModel,
        mediaPlayer: MediaPlayer,
        context: Context,
        audioAttributes: AudioAttributes,
        onCompletionListener: MediaPlayer.OnCompletionListener,
        onErrorListener: MediaPlayer.OnErrorListener
    ) {
        resetSong(mediaPlayer)
        setDataSource(
            song,
            mediaPlayer,
            context,
            audioAttributes,
            onCompletionListener,
            onErrorListener
        )
        prepareSong(mediaPlayer)
        playSong(mediaPlayer)
    }

    @Throws(
        IOException::class,
        IllegalArgumentException::class,
        IllegalStateException::class,
        SecurityException::class
    )
    private fun setDataSource(
        song: SongModel,
        mediaPlayer: MediaPlayer,
        context: Context,
        audioAttributes: AudioAttributes,
        onCompletionListener: MediaPlayer.OnCompletionListener,
        onErrorListener: MediaPlayer.OnErrorListener
    ) {
        mediaPlayer.apply {
            setDataSource(context, getSongUri(song))
            setAudioAttributes(audioAttributes)
            prepare()
            setOnCompletionListener(onCompletionListener)
            setOnErrorListener(onErrorListener)
        }
    }

    @Throws(IOException::class, IllegalStateException::class)
    private fun prepareSong(mediaPlayer: MediaPlayer) {
        mediaPlayer.prepare()
    }

    private fun resetSong(mediaPlayer: MediaPlayer) {
        mediaPlayer.reset()
    }

    private fun getSongUri(song: SongModel): Uri =
        ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, song.id)

    @Throws(IllegalStateException::class)
    private fun playSong(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()
    }

    @Throws(IllegalStateException::class)
    private fun resumeSong(mediaPlayer: MediaPlayer) {
        playSong(mediaPlayer)
    }

    //endregion

}