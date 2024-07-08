package com.dungnm.example.compose.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import com.dungnm.example.compose.R

abstract class BaseActivity : ComponentActivity() {

    @StringRes open var titleRes : Int = R.string.app_name

    abstract val viewModel : BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
    }
}