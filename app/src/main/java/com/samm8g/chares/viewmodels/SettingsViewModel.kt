package com.samm8g.chares.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.samm8g.chares.data.preferences.LanguageManager
import com.samm8g.chares.data.preferences.ThemeManager
import com.samm8g.chares.repositories.ChoreRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val themeManager: ThemeManager,
    private val languageManager: LanguageManager,
    private val choreRepository: ChoreRepository
) : ViewModel() {

    val theme: StateFlow<String> = themeManager.theme.map {
        when (it) {
            1 -> "light"
            2 -> "dark"
            else -> "system"
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "system"
    )

    val language: StateFlow<String> = languageManager.language.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "en"
    )

    val dynamicTheme: StateFlow<Boolean> = themeManager.dynamicTheme.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    fun setTheme(theme: String) {
        viewModelScope.launch {
            val themeValue = when (theme) {
                "light" -> 1
                "dark" -> 2
                else -> 0
            }
            themeManager.setTheme(themeValue)
        }
    }

    fun setLanguage(language: String) {
        viewModelScope.launch {
            languageManager.setLanguage(language)
        }
    }

    fun setDynamicTheme(isDynamic: Boolean) {
        viewModelScope.launch {
            themeManager.setDynamicTheme(isDynamic)
        }
    }

    suspend fun exportChoreData(): String {
        return choreRepository.exportChoreData()
    }

    suspend fun importChoreData(jsonString: String) {
        choreRepository.importChoreData(jsonString)
    }
}

class SettingsViewModelFactory(
    private val themeManager: ThemeManager,
    private val languageManager: LanguageManager,
    private val choreRepository: ChoreRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(themeManager, languageManager, choreRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
