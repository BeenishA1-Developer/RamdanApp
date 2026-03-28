package com.example.ramdanapp.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.ramdanapp.R
import com.example.ramdanapp.data.TasbeehDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val tasbeehId = intent.getIntExtra("tasbeeh_id", -1)
        if (tasbeehId == -1) return

        val database = TasbeehDatabase.getDatabase(context)
        CoroutineScope(Dispatchers.IO).launch {
            val tasbeeh = database.tasbeehDao().getTasbeehById(tasbeehId)
            if (tasbeeh != null && !tasbeeh.isCompleted) {
                showNotification(context, tasbeeh.name)
            }
        }
    }

    private fun showNotification(context: Context, name: String) {
        val channelId = "tasbeeh_reminder"
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Tasbeeh Reminder", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Dhikr Reminder")
            .setContentText("Have you completed your $name Dhikr?")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
