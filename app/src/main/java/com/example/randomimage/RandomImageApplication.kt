package com.example.randomimage

import android.app.Application
import androidx.datastore.preferences.core.Preferences

class RandomImageApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        PreferencesRepository.initialize(this)
    }
}