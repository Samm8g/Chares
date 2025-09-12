package com.samm8g.chares.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CompletedChoreDisplayManager(private val context: Context) {

    companion object {
        private val COMPLETED_CHORE_DISPLAY_DURATION_KEY = longPreferencesKey("completed_chore_display_duration")
        const val ONE_HOUR_IN_MILLIS = 3_600_000L
        const val TWENTY_FOUR_HOURS_IN_MILLIS = 86_400_000L
        const val SEVEN_DAYS_IN_MILLIS = 604_800_000L
        const val NEVER_IN_MILLIS = -1L // Special value to indicate never display
    }

    val completedChoreDisplayDuration: Flow<Long> = context.dataStore.data
        .map { preferences ->
            preferences[COMPLETED_CHORE_DISPLAY_DURATION_KEY] ?: ONE_HOUR_IN_MILLIS
        }

    suspend fun setCompletedChoreDisplayDuration(duration: Long) {
        context.dataStore.edit { preferences ->
            preferences[COMPLETED_CHORE_DISPLAY_DURATION_KEY] = duration
        }
    }
}
