package com.nkot.todolist

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    companion object {
        lateinit var instance: Application private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}
