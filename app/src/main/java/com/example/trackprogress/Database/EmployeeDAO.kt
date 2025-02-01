package com.example.trackprogress.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EmployeeDAO {
    @Insert
    suspend fun insert(employee: Employee)

    @Update
    suspend fun update(employee: Employee)

    @Delete
    suspend fun delete(employee: Employee)

    @Query("SELECT * FROM employee")
    suspend fun getEmployee(): Employee?

    @Query("SELECT * FROM employee WHERE userId= :empID")
    suspend fun getEmployeeByID(empID: Long): Employee?

    @Query("DELETE FROM employee WHERE userId= :empID")
    suspend fun deleteEmpByID(empID: Long)

    @Query("UPDATE employee SET leaveBalance = leaveBalance - :count WHERE userId = :empID")
    suspend fun updateLeaves(empID: Long, count: Int)

    // Fixed department counts query with data class
    @Query("SELECT department, COUNT(*) as count FROM employee GROUP BY department")
    suspend fun getDepartmentCounts(): List<DepartmentCount>

    @Query("SELECT leaveBalance FROM employee WHERE userId= :empId")
    suspend fun getLeaveBalance(empId: Long): Int?

    @Query("SELECT COUNT(*) FROM employee")
    suspend fun getTotalEmployeeCount(): Int

    @Query("SELECT DISTINCT department FROM employee")
    suspend fun getAllDepartments(): List<String>
}

// Add this data class in the same file or create a new file
data class DepartmentCount(
    val department: String,
    val count: Int
)