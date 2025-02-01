package com.example.trackprogress.Admin

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Database.Status
import com.example.trackprogress.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.launch
import java.util.*

class DashboardFragment : Fragment() {
    private lateinit var totalEmployeesCard: TextView
    private lateinit var pendingTasksCard: TextView
    private lateinit var taskCompletionChart: LineChart
    private lateinit var departmentChart: PieChart
    private lateinit var leaveRequestsChart: BarChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        initializeViews(view)
        loadDashboardData()

        return view
    }

    private fun initializeViews(view: View) {
        totalEmployeesCard = view.findViewById(R.id.totalEmployeesCard)
        pendingTasksCard = view.findViewById(R.id.pendingTasksCard)
        taskCompletionChart = view.findViewById(R.id.taskCompletionChart)
        departmentChart = view.findViewById(R.id.departmentChart)
        leaveRequestsChart = view.findViewById(R.id.leaveRequestsChart)

        setupCharts()
    }

    private fun setupCharts() {
        // Setup Line Chart
        taskCompletionChart.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            legend.isEnabled = true
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            axisRight.isEnabled = false
        }

        // Setup Pie Chart
        departmentChart.apply {
            description.isEnabled = false
            setUsePercentValues(true)
            legend.isEnabled = true
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(12f)
        }

        // Setup Bar Chart
        leaveRequestsChart.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            legend.isEnabled = true
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            axisRight.isEnabled = false
        }
    }

    private fun loadDashboardData() {
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(requireContext())

            // Load basic stats
            val totalEmployees = db.employeeDao().getTotalEmployeeCount()
            val pendingTasks = db.taskDao().getTaskCountByStatus(Status.PENDING)

            totalEmployeesCard.text = totalEmployees.toString()
            pendingTasksCard.text = pendingTasks.toString()

            // Load task completion data for line chart
            val taskData = getTaskCompletionData(db)
            updateTaskCompletionChart(taskData)

            // Load department distribution for pie chart
            val departmentData = getDepartmentDistribution(db)
            updateDepartmentChart(departmentData)

            // Load leave requests for bar chart
            val leaveData = getLeaveRequestsData(db)
            updateLeaveRequestsChart(leaveData)
        }
    }

    private suspend fun getTaskCompletionData(db: AppDatabase): List<Entry> {
        val completedTasks = db.taskCompletionDao().getCompletionsByMonth()
        return completedTasks.mapIndexed { index, count ->
            Entry(index.toFloat(), count.toFloat())
        }
    }

    private fun updateTaskCompletionChart(entries: List<Entry>) {
        val dataSet = LineDataSet(entries, "Completed Tasks").apply {
            color = ColorTemplate.MATERIAL_COLORS[0]
            setCircleColor(ColorTemplate.MATERIAL_COLORS[0])
            lineWidth = 2f
            circleRadius = 4f
            setDrawCircleHole(false)
            valueTextSize = 10f
        }

        taskCompletionChart.data = LineData(dataSet)
        taskCompletionChart.invalidate()
    }

    private suspend fun getDepartmentDistribution(db: AppDatabase): List<PieEntry> {
        val departments = db.employeeDao().getDepartmentCounts()
        return departments.map { dept ->
            PieEntry(dept.count.toFloat(), dept.department)
        }
    }

    private fun updateDepartmentChart(entries: List<PieEntry>) {
        val dataSet = PieDataSet(entries, "Departments").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            valueTextSize = 12f
            valueTextColor = Color.BLACK
        }

        departmentChart.data = PieData(dataSet)
        departmentChart.invalidate()
    }

    private suspend fun getLeaveRequestsData(db: AppDatabase): List<BarEntry> {
        val leaveRequests = db.pendingLeavesDao().getLeaveRequestsByMonth()
        return leaveRequests.mapIndexed { index, count ->
            BarEntry(index.toFloat(), count.toFloat())
        }
    }

    private fun updateLeaveRequestsChart(entries: List<BarEntry>) {
        val dataSet = BarDataSet(entries, "Leave Requests").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            valueTextSize = 10f
        }

        leaveRequestsChart.data = BarData(dataSet)
        leaveRequestsChart.invalidate()
    }
}