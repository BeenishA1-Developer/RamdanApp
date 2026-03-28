package com.example.ramdanapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasbeeh")
data class Tasbeeh(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val targetCount: Int,
    val currentCount: Int = 0,
    val reminderTime: Long, // Milliseconds since start of day or absolute time
    val isCompleted: Boolean = false
)
