package com.nkot.todolist.repository

import com.nkot.todolist.database.Task.TaskDao
import com.nkot.todolist.database.Task.TaskEntity
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

        val getAll = taskDao.getAll()

        suspend fun insert(task: TaskEntity) {
            taskDao.insert(task)
        }

        suspend fun update(task: TaskEntity) {
            taskDao.update(task)
        }

        suspend fun delete(task: TaskEntity) {
            taskDao.delete(task)
        }

       fun getById(id: Int) = taskDao.getById(id)
}
