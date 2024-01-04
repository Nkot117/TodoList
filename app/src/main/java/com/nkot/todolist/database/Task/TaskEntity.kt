package com.nkot.todolist.database.Task

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String? = null,
    val completed: Boolean,
    val deadline: Date? = null,
    val created: Date,
)

fun TaskEntity.deadlineToString(): String? {
    return deadline?.let {
        val formatter = java.text.SimpleDateFormat("yyyy/MM/dd", java.util.Locale.JAPAN)
        formatter.format(it)
    }
}
