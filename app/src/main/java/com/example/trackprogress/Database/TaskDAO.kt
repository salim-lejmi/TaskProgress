package com.example.trackprogress.Database

import androidx.lifecycle.LiveData
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

    @Query("SELECT * FROM task WHERE userId = :taskId")
    suspend fun getTaskByID(taskId: Long): Task?

    @Query("DELETE FROM task WHERE id= :userId")
    suspend fun deleteTaskById(userId: Long)
    @Query("SELECT * FROM task WHERE id= :taskId")
    suspend fun getTaskByTaskId(taskId: Long): Task?

    @Query("SELECT * FROM task WHERE userId = :userId")
    fun getTask(userId: Long): LiveData<List<Task>>

    @Query("SELECT COUNT(*) FROM task WHERE userId= :userId")
    suspend fun getAssignedTaskCount(userId: Long): Int

    @Query("SELECT COUNT(*) FROM task WHERE userId= :userId AND status = 'COMPLETED'")
    suspend fun getCompletedTaskCount(userId: Long): Int
}