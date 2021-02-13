package com.gmail.ivantsov.nikolai.my_mp3.framework.navigator

import androidx.navigation.NavController
import com.gmail.ivantsov.nikolai.my_mp3.R

class Navigator {
    fun navigateToSettings(navController: NavController) {
        navController.navigate(R.id.playFragment)
    }

    fun navigateToPlayingSong(navController: NavController) {
        navController.navigate(R.id.settingsFragment)
    }
}