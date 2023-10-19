package com.example.trackprogress.Database

import android.net.wifi.hotspot2.pps.Credential.UserCredential
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface UserCredsDAO {
    @Insert
    suspend fun insertUserCredentials(userCred: UserCredentials)

    @Update
    suspend fun updateUserCred(userCred: UserCredential)

    @Delete
    suspend fun deleteUserCred(userCred: UserCredential)

}