package com.example.trackprogress.Database

import android.content.Context
import android.net.wifi.hotspot2.pps.Credential.UserCredential
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Task::class, UserCredentials::class, TaskCompletion::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun taskDao(): TaskDAO
    abstract fun userCredsDao(): UserCredsDAO
    abstract fun taskCompletionDao(): TaskCompletionDAO

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
            }
            return instance as AppDatabase
        }
    }
}