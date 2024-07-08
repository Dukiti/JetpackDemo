package com.dungnm.example.compose.ui.activity.setting

import android.content.Context
import com.dungnm.example.compose.constants.Tags
import com.dungnm.example.compose.ui.base.BaseViewModel
import com.dungnm.example.compose.utils.Storage
import com.dungnm.example.compose.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
) : BaseViewModel() {

    private val _languageSelected = MutableStateFlow(Tags.LANG_EN)
    val languageSelected: StateFlow<String> = _languageSelected

    private val _themeSelected = MutableStateFlow(Tags.THEME_LIGHT)
    val themeSelected: StateFlow<String> = _themeSelected


    init {
        val lang = Storage.getInstance().getString(Tags.LANGUAGE, Tags.LANG_EN) ?: Tags.LANG_EN
        _languageSelected.value = lang

        val theme =
            Storage.getInstance().getString(Tags.THEME, Tags.THEME_LIGHT) ?: Tags.THEME_LIGHT
        _themeSelected.value = theme
    }


    fun updateLanguage(lang: String, context: Context) {
        _languageSelected.value = lang
        Storage.getInstance().putString(Tags.LANGUAGE, lang)
        Utils.changeLanguage(lang, context)
    }

    fun updateTheme(theme: String) {
        _themeSelected.value = theme
        currentTheme.value = theme
        Storage.getInstance().putString(Tags.THEME, theme)
    }
}