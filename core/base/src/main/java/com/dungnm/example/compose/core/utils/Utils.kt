package com.dungnm.example.compose.core.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.Locale

object Utils {
    fun changeLanguage(lang: String, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Use LocaleManager for Android 12 and later
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(lang)
        } else {
            // For earlier versions, update the app's configuration manually
            val locale = Locale(lang)
            Locale.setDefault(locale)

            val resources = context.resources
            val configuration = resources.configuration
            configuration.setLocale(locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
    }
}