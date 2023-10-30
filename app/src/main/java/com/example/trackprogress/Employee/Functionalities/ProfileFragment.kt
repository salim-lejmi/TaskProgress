package com.example.trackprogress.Employee.Functionalities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.R
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class ProfileFragment : Fragment() {

    lateinit var txtProfileName: TextView
    lateinit var txtProfileEmail: TextView
    lateinit var txtProfileDepartment: TextView
    lateinit var txtProfileDOJ: TextView
    lateinit var txtProfileSalary: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        txtProfileName = view.findViewById(R.id.txtProfileName)
        txtProfileEmail = view.findViewById(R.id.txtProfileEmail)
        txtProfileDepartment = view.findViewById(R.id.txtProfileDepartment)
        txtProfileDOJ = view.findViewById(R.id.txtProfileDOJ)
        txtProfileSalary = view.findViewById(R.id.txtProfileSalary)

        val id = arguments?.getLong("ID")!!

        val userDAO = AppDatabase.getInstance(requireContext()).userDao()
        val employeeDAO = AppDatabase.getInstance(requireContext()).employeeDao()
        lifecycleScope.launch {
            val user = userDAO.getUserById(id)!!
            val employee = employeeDAO.getEmployeeByID(id)!!
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            txtProfileName.text = "${user.name}\n(${employee.designation})"
            txtProfileEmail.text = user.email
            txtProfileDepartment.text = employee.department
            txtProfileDOJ.text = dateFormat.format(employee.joiningDate)
            txtProfileSalary.text = employee.salary.toString()

        }

        return view
    }
}