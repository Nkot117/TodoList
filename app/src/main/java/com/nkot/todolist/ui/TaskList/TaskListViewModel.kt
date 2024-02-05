package com.nkot.todolist.ui.TaskList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkot.todolist.database.Task.TaskEntity
import com.nkot.todolist.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    val allTasks: Flow<List<TaskEntity>> = taskRepository.getAll
    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            taskRepository.delete(task)
        }
    }

    fun changeTaskStatus(task: TaskEntity) {
        viewModelScope.launch {
            taskRepository.update(task.copy(completed = !task.completed))
        }
    }
}
