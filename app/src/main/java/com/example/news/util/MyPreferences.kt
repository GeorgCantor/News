package com.example.news.util

import android.content.Context
import androidx.preference.PreferenceManager

class MyPreferences(context: Context?) {

    companion object {
        private const val DARK_MODE = "dark_mode"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var darkMode = preferences.getInt(DARK_MODE, 0)
        set(value) = preferences.edit().putInt(DARK_MODE, value).apply()
}