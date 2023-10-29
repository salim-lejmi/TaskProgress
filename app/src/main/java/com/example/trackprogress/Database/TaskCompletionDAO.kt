package com.example.trackprogress.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.Date

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

@Query("SELECT completionDate FROM taskcompletion WHERE taskId = :id")
suspend fun getCompletionDateByTaskId(id: Long): Date?
}