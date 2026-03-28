package com.example.ramdanapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TasbeehDao {

    @Query("SELECT * FROM tasbeeh")
    fun getAllTasbeehs(): Flow<List<Tasbeeh>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasbeeh(tasbeeh: Tasbeeh)

    @Update
    suspend fun updateTasbeeh(tasbeeh: Tasbeeh)

    @Query("UPDATE tasbeeh SET currentCount = 0, isCompleted = 0")
    suspend fun resetDailyCounts()

    @Query("SELECT * FROM tasbeeh WHERE id = :id")
    suspend fun getTasbeehById(id: Int): Tasbeeh?
}
