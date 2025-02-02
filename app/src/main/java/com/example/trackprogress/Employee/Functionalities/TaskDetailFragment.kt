package com.example.trackprogress.Employee.Functionalities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Database.Notification
import com.example.trackprogress.Database.NotificationType
import com.example.trackprogress.Database.Status
import com.example.trackprogress.Database.Task
import com.example.trackprogress.Database.TaskCompletion
import com.example.trackprogress.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskDetailFragment : Fragment() {

    lateinit var txtTaskTitle1: TextView
    lateinit var txtTaskDueDate1: TextView
    lateinit var txtTaskStatus1: TextView
    lateinit var txtTaskDescription1: TextView
    lateinit var btnUpdateStatus: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_detail, container, false)
        txtTaskTitle1 = view.findViewById(R.id.txtTaskTitle1)
        txtTaskDueDate1 = view.findViewById(R.id.txtTaskDueDate1)
        txtTaskStatus1 = view.findViewById(R.id.txtTaskStatus1)
        txtTaskDescription1 = view.findViewById(R.id.txtTaskDescription1)
        btnUpdateStatus = view.findViewById(R.id.btnUpdateStatus)
        val taskId = arguments?.getLong("taskID")!!

        val taskDAO = AppDatabase.getInstance(requireContext()).taskDao()
        lifecycleScope.launch {
            val task = taskDAO.getTaskByTaskId(taskId)
            val title = task?.title!!
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val dueDate = dateFormat.format(task?.dueDate)
            val status = task?.status.toString()
            val description = task?.description!!
            txtTaskTitle1.setText(title)
            txtTaskDueDate1.setText(dueDate)
            txtTaskStatus1.setText(status)
            txtTaskDescription1.setText(description)

            if(status.equals("COMPLETED")){
                btnUpdateStatus.visibility = View.GONE
            }
        }
        btnUpdateStatus.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val task = taskDAO.getTaskByTaskId(taskId)
                val userId = task?.userId!!
                val title = task?.title!!
                val dueDate = task?.dueDate!!
                val description = task?.description!!
                taskDAO.updateTask(Task(taskId, userId, title, description, dueDate, Status.COMPLETED))

                val taskCompletionDAO = AppDatabase.getInstance(requireContext()).taskCompletionDao()
                val completionDate = stringToDate(getCurrentDate())!!
                taskCompletionDAO.insertTaskCompletion(TaskCompletion(0, taskId, userId, completionDate))

                // Add notification for admin
                val userDao = AppDatabase.getInstance(requireContext()).userDao()
                val notificationDao = AppDatabase.getInstance(requireContext()).notificationDao()
                val employee = userDao.getUserById(userId)

                val notification = Notification(
                    id = 0,
                    userId = 1000, // Admin ID
                    title = "Task Completed",
                    message = "${employee?.name} has completed the task: $title",
                    timestamp = Date(),
                    isRead = false,
                    type = NotificationType.TASK_ASSIGNED
                )
                notificationDao.insertNotification(notification)

                Toast.makeText(requireContext(),"Task status Updated",Toast.LENGTH_SHORT).show()
            }
        }

        return view

    }
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date(System.currentTimeMillis())
        return dateFormat.format(date)
    }

    fun stringToDate(dateSting: String): Date?{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try{
            return dateFormat.parse(dateSting)
        }catch (e: ParseException){
            e.printStackTrace()
        }
        return null
    }
}