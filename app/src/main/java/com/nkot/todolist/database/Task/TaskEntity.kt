package com.nkot.todolist.database.Task

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String? = null,
    val completed: Boolean,
    val created: Date,
)
