package com.example.trackprogress.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskCompletionDAO {
@Insert
suspend fun insertTaskCompletion(taskComp: TaskCompletion)

@Update
suspend fun updateTaskCompletion(taskComp: TaskCompletion)

@Delete
suspend fun  deleteTaskCompletion(taskCompletion: TaskCompletion)

@Query("SELECT * FROM taskcompletion WHERE id = :taskId")
suspend fun getTaskCompletionByID(taskId: Long): TaskCompletion?

@Query("DELETE FROM taskcompletion WHERE userId = :userId")
suspend fun deleteTaskCompletionById(userId: Long)
}