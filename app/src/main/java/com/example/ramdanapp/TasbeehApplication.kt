package com.example.ramdanapp

import android.app.Application
import com.example.ramdanapp.data.AppContainer
import com.example.ramdanapp.data.AppDataContainer

class TasbeehApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}