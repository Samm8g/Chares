package com.example.chares.repositories

import com.example.chares.data.Chore
import com.example.chares.data.ChoreDao
import kotlinx.coroutines.flow.Flow

class ChoreRepository(private val choreDao: ChoreDao) {
    val allChores: Flow<List<Chore>> = choreDao.getAllChores()

    suspend fun insert(chore: Chore) {
        choreDao.insertChore(chore)
    }

    suspend fun update(chore: Chore) {
        choreDao.updateChore(chore)
    }

    suspend fun delete(chore: Chore) {
        choreDao.deleteChore(chore)
    }
}
