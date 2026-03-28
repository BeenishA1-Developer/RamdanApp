package com.example.ramdanapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.ramdanapp.TasbeehApplication

class ResetTasbeehWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val repository = (applicationContext as TasbeehApplication).container.tasbeehRepository
        return try {
            repository.resetAllCounts()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}