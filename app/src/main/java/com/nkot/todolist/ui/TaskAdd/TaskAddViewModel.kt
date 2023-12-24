package com.nkot.todolist.ui.TaskAdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nkot.todolist.database.Task.TaskDao
import com.nkot.todolist.database.Task.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskAddViewModel(private val taskDao: TaskDao) : ViewModel() {

    fun addTask(title: String, description: String?) {
        val newTask = TaskEntity(
            title = title,
            description = description,
            completed = false,
            created = java.util.Date()
        )

        viewModelScope.launch {
            taskDao.insert(newTask)
        }
    }

    fun getTask(id: Int): Flow<TaskEntity> {
        return taskDao.getById(id)
    }

    fun updateTask(taskEntity: TaskEntity) {
        viewModelScope.launch {
            taskDao.update(taskEntity)
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
