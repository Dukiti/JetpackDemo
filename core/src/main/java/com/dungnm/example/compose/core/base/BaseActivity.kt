package com.dungnm.example.compose.core.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.dungnm.example.compose.core.Storage
import com.dungnm.example.compose.core.constants.Tags
import com.dungnm.example.compose.core.ui.theme.MainAppTheme
import com.dungnm.example.compose.core.utils.Utils
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseActivity : ComponentActivity() {

    abstract val titleRes: Int

    abstract val viewModel: BaseViewModel

    private val currentTheme = MutableStateFlow(Tags.THEME_LIGHT)


    override fun onCreate(savedInstanceState: Bundle?) {
        val lang = Storage.getInstance().getString(Tags.LANGUAGE, Tags.LANG_EN) ?: Tags.LANG_EN
        Utils.changeLanguage(lang, this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        updateTheme()
        super.onCreate(savedInstanceState)
    }

    fun updateTheme() {
        val theme =
            Storage.getInstance().getString(Tags.THEME) ?: Tags.THEME_LIGHT
        currentTheme.value = theme
    }

    fun setContentView(builder: NavGraphBuilder.(NavHostController) -> Unit) {
        setContent {
            val currentTheme by currentTheme.collectAsState()
            MainAppTheme(theme= currentTheme, builder = builder)
        }
    }
}