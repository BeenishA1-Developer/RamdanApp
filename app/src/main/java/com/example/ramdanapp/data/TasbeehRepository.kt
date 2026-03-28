package com.example.ramdanapp.data

import kotlinx.coroutines.flow.Flow

class TasbeehRepository(private val tasbeehDao: TasbeehDao) {
    val allTasbeehs: Flow<List<Tasbeeh>> = tasbeehDao.getAllTasbeehs()

    suspend fun insert(tasbeeh: Tasbeeh) {
        tasbeehDao.insertTasbeeh(tasbeeh)
    }

    suspend fun update(tasbeeh: Tasbeeh) {
        tasbeehDao.updateTasbeeh(tasbeeh)
    }

    suspend fun resetDailyCounts() {
        tasbeehDao.resetDailyCounts()
    }

    suspend fun getTasbeehById(id: Int): Tasbeeh? {
        return tasbeehDao.getTasbeehById(id)
    }
}
