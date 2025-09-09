package com.samm8g.chares.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.samm8g.chares.data.Chore
import com.samm8g.chares.repositories.ChoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ChoreViewModel(private val repository: ChoreRepository) : ViewModel() {

    val allChores: Flow<List<Chore>> = repository.allChores

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
