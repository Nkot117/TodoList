package com.nkot.todolist

import android.app.Application
import com.nkot.todolist.database.AppDatabase

class BaseApplication : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
}