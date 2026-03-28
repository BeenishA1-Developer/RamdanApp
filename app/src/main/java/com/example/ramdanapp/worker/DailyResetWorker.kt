package com.example.ramdanapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.ramdanapp.data.TasbeehDatabase
import com.example.ramdanapp.data.TasbeehRepository

class DailyResetWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val database = TasbeehDatabase.getDatabase(applicationContext)
        val repository = TasbeehRepository(database.tasbeehDao())
        return try {
            repository.resetDailyCounts()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
