package com.example.trackprogress.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val name: String,
    val email: String,
    val userType: UserType
)

@Entity(tableName = "userCredentials")
data class UserCredentials(
    @PrimaryKey val username: String,
    val password: String
)

@Entity(tableName = "employee")
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val userId: Long, // Id of User table
    val department: String,
    val joiningDate: Date,
    val dateOfBirth: Date,
    val designation: String,
    val salary: Double,
    val leaveBalance: Int
)

@Entity(tableName = "Task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val dueDate: Date,
    val status: Status,
    val userId: Long
)

@Entity(tableName = "TaskCompletion")
data class TaskCompletion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val taskId: Long,
    val completionDate: Date
)

enum class UserType{
    ADMIN,
    EMPLOYEE
}
enum class Status{
    PENDING,
    COMPLETED
}