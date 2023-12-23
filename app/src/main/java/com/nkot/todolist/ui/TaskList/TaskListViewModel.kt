package com.nkot.todolist.ui.TaskList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nkot.todolist.database.Task.TaskDao
import com.nkot.todolist.database.Task.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskListViewModel(private val taskDao:  TaskDao) : ViewModel() {
    val allTasks: Flow<List<TaskEntity>> = taskDao.getAll()
}

class TaskListViewModelFactory(private val taskDao: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskListViewModel(taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}