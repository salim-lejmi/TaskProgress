package com.example.trackprogress.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDAO {
    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun  deleteTask(task: Task)

    @Query("SELECT * FROM task WHERE id = :taskId")
    suspend fun getTaskByID(taskId: Long): Task?
}