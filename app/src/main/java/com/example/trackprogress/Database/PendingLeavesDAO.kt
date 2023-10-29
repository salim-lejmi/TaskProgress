package com.example.trackprogress.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PendingLeavesDAO {

    @Insert
    suspend fun insert(pendingLeaves: PendingLeaves)

    @Update
    suspend fun update(pendingLeaves: PendingLeaves)

    @Delete
    suspend fun delete(pendingLeaves: PendingLeaves)

    @Query("DELETE FROM pendingLeaves WHERE userId= :userId")
    suspend fun deleteLeaveByUserId(userId: Long)

    @Query("DELETE FROM pendingLeaves WHERE id= :leaveId")
    suspend fun deleteLeaveByLeaveId(leaveId: Long)

    @Query("SELECT * FROM pendingLeaves WHERE id= :userId")
    suspend fun getLeavesByUserId(userId: Long): PendingLeaves?

    @Query("SELECT * FROM pendingLeaves WHERE userId= :userId")
    fun displayLeaves(userId: Long): LiveData<List<PendingLeaves>>

    @Query("SELECT SUM(count) FROM pendingLeaves WHERE userId = :userId")
    suspend fun getSumOfLeavesByUserId(userId: Long): Int

}