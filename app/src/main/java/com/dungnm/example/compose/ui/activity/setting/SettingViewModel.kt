package com.dungnm.example.compose.ui.activity.setting

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.dungnm.example.compose.constants.Tags
import com.dungnm.example.compose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val sdf: SharedPreferences
) : BaseViewModel() {

    private val _languageSelected = MutableStateFlow(Tags.LANG_EN)
    val languageSelected: StateFlow<String> = _languageSelected

    private val _themeSelected = MutableStateFlow(Tags.THEME_LIGHT)
    val themeSelected: StateFlow<String> = _themeSelected

    init {
        val lang = sdf.getString(Tags.LANGUAGE, Tags.LANG_EN) ?: Tags.LANG_EN
        _languageSelected.value = lang

        val theme = sdf.getString(Tags.THEME, Tags.THEME_LIGHT) ?: Tags.THEME_LIGHT
        _themeSelected.value = theme
    }


    fun updateLanguage(lang: String) {
        _languageSelected.value = lang
        sdf.edit().putString(Tags.LANGUAGE, lang).apply()
    }

    fun updateTheme(theme: String) {
        _themeSelected.value = theme
        sdf.edit().putString(Tags.THEME, theme).apply()
    }
}