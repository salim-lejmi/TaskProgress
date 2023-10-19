package com.example.trackprogress.Database

import android.net.wifi.hotspot2.pps.Credential.UserCredential
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Task::class, UserCredentials::class, TaskCompletion::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun taskDao(): TaskDAO
    abstract fun userCredsDao(): UserCredsDAO
    abstract fun taskCompletionDao(): TaskCompletionDAO
}