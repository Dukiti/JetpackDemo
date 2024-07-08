package com.dungnm.example.compose

import android.app.Application
import com.dungnm.example.compose.utils.Storage
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Storage.getInstance().init(this)
    }
}