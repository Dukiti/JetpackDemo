package com.dungnm.example.compose.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import com.dungnm.example.compose.R

abstract class BaseActivity : ComponentActivity() {

    @StringRes open var titleRes : Int = R.string.app_name

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        super.onCreate(savedInstanceState)
    }
}