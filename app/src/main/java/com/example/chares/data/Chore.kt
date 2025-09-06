package com.example.chares.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chores")
data class Chore(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val completedAt: Long? = null
)
