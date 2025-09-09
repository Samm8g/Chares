package com.samm8g.chares.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChoreDao {
    @Query("SELECT * FROM chores ORDER BY isCompleted ASC, id DESC")
    fun getAllChores(): Flow<List<Chore>>

    @Insert
    suspend fun insertChore(chore: Chore)

    @Update
    suspend fun updateChore(chore: Chore)

    @Delete
    suspend fun deleteChore(chore: Chore)

    @Insert
    suspend fun insertAll(chores: List<Chore>)

    @Query("SELECT * FROM chores")
    suspend fun getAllChoresOnce(): List<Chore>

    @Query("DELETE FROM chores")
    suspend fun deleteAllChores()
}
