package com.example.trackprogress.Database

import android.net.wifi.hotspot2.pps.Credential.UserCredential
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserCredsDAO {
    @Insert
    suspend fun insertUserCredentials(userCred: UserCredentials)

    @Update
    suspend fun updateUserCred(userCred: UserCredentials)

    @Delete
    suspend fun deleteUserCred(userCred: UserCredentials)

    @Query("SELECT COUNT(*) FROM  userCredentials WHERE username = 'Admin'")
    suspend fun adminExists(): Int

    @Query("SELECT * FROM userCredentials WHERE username = :uname")
    suspend fun getCredByUsername(uname: String): UserCredentials?

    @Query("DELETE FROM userCredentials WHERE username= :uname")
    suspend fun deleteUserCredByEmail(uname: String)

}