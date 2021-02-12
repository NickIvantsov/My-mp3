package com.gmail.ivantsov.nikolai.my_mp3.presentation

import android.Manifest
import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gmail.ivantsov.nikolai.my_mp3.databinding.ActivitySplashBinding
import com.google.android.material.snackbar.Snackbar


class SplashActivity : AppCompatActivity() {
    companion object {
        private const val NAVIGATION_DELAY_MILLIS = 2_000L
        private const val READ_STORAGE_PERMISSION_CODE = 1
    }


    private var bindingImpl: ActivitySplashBinding? = null
    private val binding get() = bindingImpl!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingImpl = ActivitySplashBinding.inflate(layoutInflater)
        makeFullScreen()
        setContentView(binding.root)
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_STORAGE_PERMISSION_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super
            .onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                navToMainActivity()
            } else {
                showMessage(com.gmail.ivantsov.nikolai.my_mp3.R.string.read_not_granted_mess)
            }
        }
    }

    private fun showMessage(message:Int) {
        Snackbar.make(
            binding.mainContainer,
            getString(message),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(
                getString(com.gmail.ivantsov.nikolai.my_mp3.R.string.ok)
            ) {
                checkPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    READ_STORAGE_PERMISSION_CODE
                )
            }.show()
    }

    // Function to check and request permission
    private fun checkPermission(permission: String, requestCode: Int) {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            )
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat
                .requestPermissions(
                    this, arrayOf(permission),
                    requestCode
                )
        } else {
            navToMainActivityWithDelay()
        }
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

    private fun navToMainActivityWithDelay() {
        // Using a handler to delay loading the MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Start activity
            navToMainActivity()

        }, NAVIGATION_DELAY_MILLIS)
    }

    private fun navToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))

        // Animate the loading of new activity
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        // Close this activity
        finish()
    }


}