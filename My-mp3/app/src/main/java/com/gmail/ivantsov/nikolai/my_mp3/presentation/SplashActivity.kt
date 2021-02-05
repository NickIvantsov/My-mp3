package com.gmail.ivantsov.nikolai.my_mp3.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.gmail.ivantsov.nikolai.my_mp3.R

class SplashActivity : AppCompatActivity() {
    companion object {
        private const val NAVIGATION_DELAY_MILLIS = 2_000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeFullScreen()
        setContentView(R.layout.activity_splash)

        // Using a handler to delay loading the MainActivity
        Handler(Looper.getMainLooper()).postDelayed({

            // Start activity
            startActivity(Intent(this, MainActivity::class.java))

            // Animate the loading of new activity
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            // Close this activity
            finish()

        }, NAVIGATION_DELAY_MILLIS)
    }

    private fun makeFullScreen() {
        // Remove Title
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Make Fullscreen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        // Hide the toolbar
        supportActionBar?.hide()
    }
}