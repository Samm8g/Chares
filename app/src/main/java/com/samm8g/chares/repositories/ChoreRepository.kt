package com.samm8g.chares.repositories

import com.samm8g.chares.data.Chore
import com.samm8g.chares.data.ChoreDao
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.ListSerializer

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

    suspend fun exportChoreData(): String {
        val chores = choreDao.getAllChoresOnce()
        return Json.encodeToString(ListSerializer(Chore.serializer()), chores)
    }

    suspend fun importChoreData(jsonString: String) {
        val chores = Json.decodeFromString(ListSerializer(Chore.serializer()), jsonString)
        choreDao.deleteAllChores()
        choreDao.insertAll(chores)
    }
}
