package com.dungnm.example.compose.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.dungnm.example.compose.R
import com.dungnm.example.compose.constants.Tags
import com.dungnm.example.compose.ui.theme.MainAppTheme
import com.dungnm.example.compose.utils.Storage
import com.dungnm.example.compose.utils.Utils

abstract class BaseActivity : ComponentActivity() {

    @StringRes
    open var titleRes: Int = R.string.app_name

    abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val lang = Storage.getInstance().getString(Tags.LANGUAGE, Tags.LANG_EN) ?: Tags.LANG_EN
        Utils.changeLanguage(lang, this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateTheme()
    }

    fun ComponentActivity.setContentView(content: @Composable () -> Unit) {
        setContent {
            val currentTheme by viewModel.currentTheme.collectAsState()
            MainAppTheme(currentTheme = currentTheme) {
                content.invoke()
            }
        }
    }
}