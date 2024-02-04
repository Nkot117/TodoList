package com.nkot.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nkot.todolist.database.Task.TaskDao
import com.nkot.todolist.database.Task.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
