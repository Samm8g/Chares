package com.example.chares

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.chares.data.AppDatabase
import com.example.chares.data.preferences.LanguageManager
import com.example.chares.data.preferences.ThemeManager
import com.example.chares.repositories.ChoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChoreApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { ChoreRepository(database.choreDao()) }

    val languageManager by lazy { LanguageManager(applicationContext) }
    val themeManager by lazy { ThemeManager(applicationContext) }

    override fun onCreate() {
        super.onCreate()
        // Set the application locale based on user preference as early as possible
        CoroutineScope(Dispatchers.Main).launch {
            languageManager.language.collect { appLanguage ->
                Log.d("ChoreApplication", "Setting app locale to: $appLanguage")
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(appLanguage))
            }
        }
    }
}
