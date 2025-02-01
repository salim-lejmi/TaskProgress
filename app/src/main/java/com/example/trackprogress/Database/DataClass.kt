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
    val id: Long = 0,
    val userId: Long,
    val title: String,
    val description: String,
    val dueDate: Date,
    val status: Status
)

@Entity(tableName = "TaskCompletion")
data class TaskCompletion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val taskId: Long,
    val userId: Long,
    val completionDate: Date
)

@Entity(tableName = "pendingLeaves")
data class PendingLeaves(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var userId: Long,
    var fromDate: Date,
    var count: Int,
    var appliedDate: Date
)


@Entity(tableName = "queryTable")
data class RaiseQuery(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val userQuery: String,
    val adminReply: String,
    val status: Status

)
@Entity(tableName = "notifications")
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val title: String,
    val message: String,
    val timestamp: Date,
    val isRead: Boolean = false,
    val type: NotificationType
)

enum class NotificationType {
    TASK_ASSIGNED,
    LEAVE_STATUS,
    QUERY_REPLY
}

enum class UserType{
    ADMIN,
    EMPLOYEE
}
enum class Status{
    PENDING,
    COMPLETED
}