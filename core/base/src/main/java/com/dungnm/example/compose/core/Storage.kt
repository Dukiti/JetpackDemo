package com.dungnm.example.compose.core

import android.content.Context
import android.content.SharedPreferences

class Storage {

    companion object {
        private var instance: Storage? = null
        fun getInstance(): Storage {
            if (instance == null) {
                instance = Storage()
            }
            return instance!!
        }
    }

    private var sdf: SharedPreferences? = null

    fun init(context: Context) {
        sdf = context.getSharedPreferences("preferences_name", Context.MODE_PRIVATE)
    }


    fun getString(tag: String, defaultValue: String? = null): String? {
        return sdf?.getString(tag, defaultValue)
    }

    fun putString(tag: String, value: String?) {
        sdf?.edit()?.putString(tag, value)?.apply()
    }
}