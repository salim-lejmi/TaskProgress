package com.example.trackprogress.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RaiseQueryDAO {

    @Insert
    suspend fun insert(query: RaiseQuery)

    @Update
    suspend fun update(query: RaiseQuery)

    @Delete
    suspend fun delete(query: RaiseQuery)

    @Query("SELECT * FROM queryTable WHERE userId= :userId ")
    fun displayQuery(userId: Long): LiveData<List<RaiseQuery>>

    @Query("SELECT * FROM queryTable WHERE userId= :userId")
    suspend fun getQueryByUserId(userId: Long): RaiseQuery?

    @Query("SELECT * FROM queryTable WHERE id= :taskId")
    suspend fun getQueryByTaskId(taskId: Long): RaiseQuery

}