package com.example.ramdanapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tasbeeh::class], version = 1, exportSchema = false)
abstract class TasbeehDatabase : RoomDatabase() {
    abstract fun tasbeehDao(): TasbeehDao

    companion object {
        @Volatile
        private var Instance: TasbeehDatabase? = null

        fun getDatabase(context: Context): TasbeehDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    TasbeehDatabase::class.java,
                    "tasbeeh_db"
                ).build().also { Instance = it }
            }
        }
    }
}
