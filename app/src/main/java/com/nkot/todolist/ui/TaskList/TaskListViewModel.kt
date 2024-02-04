package com.nkot.todolist.ui.TaskList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkot.todolist.database.Task.TaskDao
import com.nkot.todolist.database.Task.TaskEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(private val taskDao:  TaskDao) : ViewModel() {
    val allTasks: Flow<List<TaskEntity>> = taskDao.getAll()
    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
           taskDao.delete(task)
        }
    }

    fun changeTaskStatus(task: TaskEntity) {
        viewModelScope.launch {
            taskDao.update(task.copy(completed = !task.completed))
        }
    }
}
