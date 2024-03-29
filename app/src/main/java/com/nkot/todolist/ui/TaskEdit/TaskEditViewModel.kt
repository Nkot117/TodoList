package com.nkot.todolist.ui.TaskEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkot.todolist.database.Task.TaskEntity
import com.nkot.todolist.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskEditViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    var editTask: TaskEntity? = null

    fun getTask(id: Int): Flow<TaskEntity> {
        return taskRepository.getById(id)
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
            taskRepository.update(newTask)
        }
    }
}
