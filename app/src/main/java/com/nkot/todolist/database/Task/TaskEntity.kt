package com.nkot.todolist.database.Task

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String? = null,
    val completed: Boolean,
    val deadline: Date? = null,
    val created: Date,
){
    companion object{
        fun getFormattedStringToDate(dateString: String): Date {
            val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)
            return formatter.parse(dateString) as Date
        }
    }
}

fun TaskEntity.deadlineToString(): String? {
    return deadline?.let {
        val formatter = SimpleDateFormat("yyyy/MM/dd", java.util.Locale.JAPAN)
        formatter.format(it)
    }
}
