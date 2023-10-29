package com.example.trackprogress.Admin.Functionalities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.trackprogress.Admin.Adapters.TaskDetailsAdapter
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.R
import kotlinx.coroutines.launch

class AdminTaskDetailFragment : Fragment() {
    lateinit var txtTasksAssigned: TextView
    lateinit var txtTasksPending: TextView
    lateinit var lstTaskDetails: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_task_detail, container, false)
        txtTasksAssigned = view.findViewById(R.id.txtTasksAssigned)
        txtTasksPending = view.findViewById(R.id.txtTasksPending)
        lstTaskDetails = view.findViewById(R.id.lstTaskDetails)

        val id = arguments?.getLong("ID")!!
        val taskDAO = AppDatabase.getInstance(requireContext()).taskDao()
        lifecycleScope.launch {
            txtTasksAssigned.text = taskDAO.getAssignedTaskCount(id).toString()
            txtTasksPending.text = taskDAO.getCompletedTaskCount(id).toString()
        }
        taskDAO.getTask(id).observe(viewLifecycleOwner){
            val adapter = TaskDetailsAdapter(requireContext(),R.layout.custom_task_detail_view,it)
            lstTaskDetails.adapter = adapter
        }

        return view
    }
}