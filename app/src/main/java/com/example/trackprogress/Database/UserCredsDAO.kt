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
    suspend fun insertUserCredentials(userCred: UserCredential)

    @Update
    suspend fun updateUserCred(userCred: UserCredential)

    @Delete
    suspend fun deleteUserCred(userCred: UserCredential)

}