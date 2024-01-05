package com.nkot.todolist.ui.TaskEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nkot.todolist.database.Task.TaskDao
import com.nkot.todolist.database.Task.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskAddViewModel(private val taskDao: TaskDao) : ViewModel() {
    var editTask: TaskEntity? = null

    fun getTask(id: Int): Flow<TaskEntity> {
        return taskDao.getById(id)
    }

    fun updateTask(title: String, description: String?, deadline: String?) {
        val taskEntity = editTask ?: return
        val formattedDeadline = deadline?.let {
            TaskEntity.getFormattedStringToDate(it)
        }
        val newTask = taskEntity.copy(
            title = title,
            description = description,
            deadline = formattedDeadline
        )


        viewModelScope.launch {
            taskDao.update(newTask)
        }
    }
}

class TaskEditViewModelFactory(private val taskDao: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskAddViewModel::class.java)) {
            return TaskAddViewModel(taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
