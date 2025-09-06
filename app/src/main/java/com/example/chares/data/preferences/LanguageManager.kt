package com.example.chares.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LanguageManager(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val LANGUAGE_KEY = stringPreferencesKey("language_option")
    }

    val language: Flow<String> = dataStore.data.map {
        preferences ->
        preferences[LANGUAGE_KEY] ?: "en" // Default to English
    }

    suspend fun setLanguage(languageValue: String) {
        dataStore.edit {
            preferences ->
            preferences[LANGUAGE_KEY] = languageValue
        }
    }
}