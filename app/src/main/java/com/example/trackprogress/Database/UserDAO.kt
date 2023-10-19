package com.example.trackprogress.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDAO {
@Insert
suspend fun insertUser(user: User)

@Update
suspend fun updateUser(user: User)

@Delete
suspend fun deleteUser(user: User)

@Query("SELECT * FROM user WHERE id = :userId")
suspend fun getTaskById(userId: Long): Task?
}