package com.gmail.ivantsov.nikolai.my_mp3.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.ivantsov.nikolai.my_mp3.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}