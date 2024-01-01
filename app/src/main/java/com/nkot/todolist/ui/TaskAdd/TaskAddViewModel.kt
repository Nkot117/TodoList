package com.nkot.todolist.ui.TaskAdd

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nkot.todolist.database.Task.TaskDao
import com.nkot.todolist.database.Task.TaskEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class TaskAddViewModel(private val taskDao: TaskDao) : ViewModel() {

    fun addTask(title: String, description: String?, deadline: String?) {
        val formattedDeadline = deadline?.let {
            val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)
            formatter.parse(deadline) as Date
        }

        val newTask = TaskEntity(
            title = title,
            description = description,
            completed = false,
            deadline = formattedDeadline,
            created = Date()
        )

        viewModelScope.launch {
            taskDao.insert(newTask)
        }
    }
}

class TaskAddViewModelFactory(private val taskDao: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskAddViewModel::class.java)) {
            return TaskAddViewModel(taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
