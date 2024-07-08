package com.dungnm.example.compose.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dungnm.example.compose.constants.Tags
import com.dungnm.example.compose.utils.Storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {
    val isLoading = MutableLiveData(false)
    val currentTheme = MutableStateFlow(Tags.THEME_LIGHT)

    init {
        val theme =
            Storage.getInstance().getString(Tags.THEME, Tags.THEME_LIGHT) ?: Tags.THEME_LIGHT
        currentTheme.value = theme
    }

    fun updateTheme() {
        val theme =
            Storage.getInstance().getString(Tags.THEME, Tags.THEME_LIGHT) ?: Tags.THEME_LIGHT
        if (theme != currentTheme.value) {
            currentTheme.value = theme
        }
    }
}