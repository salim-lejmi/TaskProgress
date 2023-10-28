package com.example.trackprogress.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [User::class, Task::class, UserCredentials::class, Employee::class, TaskCompletion::class, PendingLeaves::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun taskDao(): TaskDAO
    abstract fun userCredsDao(): UserCredsDAO
    abstract fun employeeDao(): EmployeeDAO
    abstract fun taskCompletionDao(): TaskCompletionDAO
    abstract fun pendingLeavesDao(): PendingLeavesDAO

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context?): AppDatabase {
            if (instance == null) {
                if (context != null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()
                }
            }
            return instance as AppDatabase
        }
    }
}