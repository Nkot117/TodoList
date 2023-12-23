package com.nkot.todolist.database.Task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

    @Query("SELECT * FROM task_table")
    fun getAll(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE id = :id")
    fun getById(id: Int): Flow<TaskEntity>

}