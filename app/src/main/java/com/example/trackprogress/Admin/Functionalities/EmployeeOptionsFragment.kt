package com.example.trackprogress.Admin.Functionalities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.trackprogress.Admin.EmployeeListFragment
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EmployeeOptionsFragment : Fragment() {
    lateinit var txtEmployeeName1: TextView
    lateinit var txtEmployeeID1: TextView
    lateinit var cstEdit: ConstraintLayout
    lateinit var cstDelete: ConstraintLayout
    lateinit var cstLeave: ConstraintLayout
    lateinit var cstTask: ConstraintLayout
    lateinit var cstQuery: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_employee_options, container, false)
        txtEmployeeID1 = view.findViewById(R.id.txtEmpID1)
        txtEmployeeName1= view.findViewById(R.id.txtEmpName1)
        cstEdit = view.findViewById(R.id.cstEdit)
        cstDelete = view.findViewById(R.id.cstRemove)
        cstLeave = view.findViewById(R.id.cstLeaves)
        cstTask = view.findViewById(R.id.cstTask)
        cstQuery = view.findViewById(R.id.cstQuery)

        val id = arguments?.getLong("ID")!!
        val name = arguments?.getString("name")
        txtEmployeeID1.text = id.toString()
        txtEmployeeName1.text = name
        Log.d("MyFragment", "ID: $id, Name: $name")
        val bundle = Bundle()
        bundle.putLong("ID", id)
        bundle.putString("name",name)


        cstEdit.setOnClickListener {
            val fragment = EditEmployeeFragment()
            fragment.arguments = bundle


            var myFrag = requireActivity().supportFragmentManager.beginTransaction()
            myFrag.replace(R.id.adminFrame, fragment)
            myFrag.commit()
        }
        cstDelete.setOnClickListener {
            val userDAO = AppDatabase.getInstance(requireContext()).userDao()
            val userCredsDAO = AppDatabase.getInstance(requireContext()).userCredsDao()
            val employeeDAO = AppDatabase.getInstance(requireContext()).employeeDao()
            val taskDAO = AppDatabase.getInstance(requireContext()).taskDao()
            val taskCompletionDAO = AppDatabase.getInstance(requireContext()).taskCompletionDao()

            GlobalScope.launch {

                val email = userDAO.getUserById(id)?.email!!
                if(email != null){
                    userCredsDAO.deleteUserCredByEmail(email)
                    userDAO.deleteUserById(id)
                    employeeDAO.deleteEmpByID(id)
                    taskDAO.deleteTaskById(id)
                    taskCompletionDAO.deleteTaskCompletionById(id)
                }

            }

            Toast.makeText(context,"Removing the emp",Toast.LENGTH_SHORT).show()

            var myFrag = requireActivity().supportFragmentManager.beginTransaction()
            myFrag.replace(R.id.adminFrame, EmployeeListFragment())
            myFrag.commit()
        }
        cstLeave.setOnClickListener {
            val fragment = ApproveLeaveFragment()
            fragment.arguments = bundle
            var myFrag = requireActivity().supportFragmentManager.beginTransaction()
            myFrag.replace(R.id.adminFrame, fragment)
            myFrag.commit()
        }
        cstTask.setOnClickListener {

            val fragment = AssignTaskFragment()
            fragment.arguments = bundle
            var myFrag = requireActivity().supportFragmentManager.beginTransaction()
            myFrag.replace(R.id.adminFrame, fragment)
            myFrag.commit()
        }
        cstQuery.setOnClickListener {
            val fragment = ResolveQueryFragment()
            fragment.arguments = bundle
            val myFrag = requireActivity().supportFragmentManager.beginTransaction()
            myFrag.replace(R.id.adminFrame,fragment)
            myFrag.commit()
        }
        return view
    }
}