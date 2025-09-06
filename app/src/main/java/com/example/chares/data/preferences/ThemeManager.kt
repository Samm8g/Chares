package com.example.chares.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeManager(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val THEME_KEY = intPreferencesKey("theme_option")
    }

    val theme: Flow<Int> = dataStore.data.map {
        preferences ->
        preferences[THEME_KEY] ?: 0 // Default to system theme
    }

    suspend fun setTheme(themeValue: Int) {
        dataStore.edit {
            preferences ->
            preferences[THEME_KEY] = themeValue
        }
    }
}