package com.samm8g.chares.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.samm8g.chares.data.Chore
import com.samm8g.chares.repositories.ChoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ChoreViewModel(private val repository: ChoreRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val allChores: Flow<List<Chore>> = repository.allChores

    init {
        viewModelScope.launch {
            allChores.first() // Wait for the first emission
            _isLoading.value = false
        }
    }

    fun insert(chore: Chore) = viewModelScope.launch {
        repository.insert(chore)
    }

    fun update(chore: Chore, isCompleted: Boolean) = viewModelScope.launch {
        val updatedChore = chore.copy(
            isCompleted = isCompleted,
            completedAt = if (isCompleted) System.currentTimeMillis() else null
        )
        repository.update(updatedChore)
    }

    fun delete(chore: Chore) = viewModelScope.launch {
        repository.delete(chore)
    }
}

class ChoreViewModelFactory(private val repository: ChoreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChoreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChoreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
