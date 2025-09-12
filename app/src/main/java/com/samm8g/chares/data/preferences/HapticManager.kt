package com.samm8g.chares.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HapticManager(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val HAPTIC_FEEDBACK_KEY = booleanPreferencesKey("haptic_feedback_option")
        val ANIMATIONS_ENABLED_KEY = booleanPreferencesKey("animations_enabled_option")
    }

    val hapticFeedback: Flow<Boolean> = dataStore.data.map {
        preferences ->
        preferences[HAPTIC_FEEDBACK_KEY] ?: true // Default to enabled
    }

    val animationsEnabled: Flow<Boolean> = dataStore.data.map {
        preferences ->
        preferences[ANIMATIONS_ENABLED_KEY] ?: true // Default to enabled
    }

    suspend fun setHapticFeedback(isHapticFeedbackEnabled: Boolean) {
        dataStore.edit {
            preferences ->
            preferences[HAPTIC_FEEDBACK_KEY] = isHapticFeedbackEnabled
        }
    }

    suspend fun setAnimationsEnabled(areAnimationsEnabled: Boolean) {
        dataStore.edit {
            preferences ->
            preferences[ANIMATIONS_ENABLED_KEY] = areAnimationsEnabled
        }
    }
}
