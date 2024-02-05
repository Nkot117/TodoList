package com.nkot.todolist.ui.TaskAdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkot.todolist.database.Task.TaskEntity
import com.nkot.todolist.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TaskAddViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    fun addTask(title: String, description: String?, deadline: String?) {
        val formattedDeadline = deadline?.let {
            TaskEntity.getFormattedStringToDate(it)
        }

        val newTask = TaskEntity(
            title = title,
            description = description,
            completed = false,
            deadline = formattedDeadline,
            created = Date()
        )

        viewModelScope.launch {
            taskRepository.insert(newTask)
        }
    }
}
