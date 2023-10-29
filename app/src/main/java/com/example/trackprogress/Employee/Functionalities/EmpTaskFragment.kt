package com.example.trackprogress.Employee.Functionalities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Employee.Adapters.TaskAdapter
import com.example.trackprogress.R

class EmpTaskFragment : Fragment() {

    lateinit var lstViewTask: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_emp_task, container, false)
        lstViewTask = view.findViewById(R.id.lstViewTask)

        val id = arguments?.getLong("ID")!!
        Log.d("EmpTaskFragment", "ID : $id")

        val taskDAO = AppDatabase.getInstance(requireContext()).taskDao()
        taskDAO.getTask(id).observe(viewLifecycleOwner){
            val adapter = TaskAdapter(requireContext(),R.layout.custom_task_view,it)
            lstViewTask.adapter = adapter
        }

        lstViewTask.setOnItemClickListener { parent, view, position, id ->
            val view = parent.get(position)
            val taskId = view.findViewById<TextView>(R.id.txtTaskID).text.toString().toLong()
            val bundle = Bundle()
            bundle.putLong("taskID",taskId)

            val fragment = TaskDetailFragment()
            fragment.arguments = bundle

            var myFrag = requireActivity().supportFragmentManager.beginTransaction()
            myFrag.replace(R.id.employeeFrame, fragment)
            myFrag.commit()
        }
        return view
    }
}