package com.example.ramdanapp.data

import android.content.Context

interface AppContainer {
    val tasbeehRepository: TasbeehRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val tasbeehRepository: TasbeehRepository by lazy {
        OfflineTasbeehRepository(TasbeehDatabase.getDatabase(context).tasbeehDao())
    }
}