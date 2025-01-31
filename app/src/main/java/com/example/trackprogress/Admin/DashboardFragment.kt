package com.example.trackprogress.Admin.Functionalities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFragment : Fragment() {

    private lateinit var txtTotalEmployees: TextView
    private lateinit var txtTotalTasks: TextView
    private lateinit var txtCompletedTasks: TextView
    private lateinit var txtPendingTasks: TextView
    private lateinit var txtLeaveRequests: TextView
    private lateinit var txtQueriesRaised: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        txtTotalEmployees = view.findViewById(R.id.txtTotalEmployees)
        txtTotalTasks = view.findViewById(R.id.txtTotalTasks)
        txtCompletedTasks = view.findViewById(R.id.txtCompletedTasks)
        txtPendingTasks = view.findViewById(R.id.txtPendingTasks)
        txtLeaveRequests = view.findViewById(R.id.txtLeaveRequests)
        txtQueriesRaised = view.findViewById(R.id.txtQueriesRaised)

        fetchDashboardData()

        return view
    }

    private fun fetchDashboardData() {
        val database = AppDatabase.getInstance(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val totalEmployees = database.employeeDao().getEmployeeCount()
            val totalTasks = database.taskDao().getTaskCount()
            val completedTasks = database.taskDao().getTaskCountByStatus("COMPLETED")
            val pendingTasks = database.taskDao().getTaskCountByStatus("PENDING")
            val leaveRequests = database.pendingLeavesDao().getPendingLeaveCount()
            val queriesRaised = database.raiseQueryDao().getQueryCount()

            withContext(Dispatchers.Main) {
                txtTotalEmployees.text = "Total Employees: $totalEmployees"
                txtTotalTasks.text = "Total Tasks: $totalTasks"
                txtCompletedTasks.text = "Completed Tasks: $completedTasks"
                txtPendingTasks.text = "Pending Tasks: $pendingTasks"
                txtLeaveRequests.text = "Pending Leave Requests: $leaveRequests"
                txtQueriesRaised.text = "Queries Raised: $queriesRaised"
            }
        }
    }
}