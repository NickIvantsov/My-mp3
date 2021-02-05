package com.gmail.ivantsov.nikolai.my_mp3.presentation

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.gmail.ivantsov.nikolai.my_mp3.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestMultiplePermissions()
    }

    fun requestMultiplePermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            1
        )
    }
}