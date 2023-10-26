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
import com.example.trackprogress.R

class EmployeeOptionsFragment : Fragment() {
    lateinit var txtEmployeeName1: TextView
    lateinit var txtEmployeeID1: TextView
    lateinit var cstEdit: ConstraintLayout
    lateinit var cstDelete: ConstraintLayout
    lateinit var cstLeave: ConstraintLayout
    lateinit var cstTask: ConstraintLayout


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

        val id = arguments?.getLong("ID")
        val name = arguments?.getString("name")
        txtEmployeeID1.text = id.toString()
        txtEmployeeName1.text = name
        Log.d("MyFragment", "ID: $id, Name: $name")


        cstEdit.setOnClickListener {
            Toast.makeText(context,"Editing the emp",Toast.LENGTH_SHORT).show()
        }
        cstDelete.setOnClickListener {
            Toast.makeText(context,"Removing the emp",Toast.LENGTH_SHORT).show()
        }
        cstLeave.setOnClickListener {
            Toast.makeText(context,"Approving leaves",Toast.LENGTH_SHORT).show()
        }
        cstTask.setOnClickListener {
            var myFrag = requireActivity().supportFragmentManager.beginTransaction()
            myFrag.replace(R.id.adminFrame, AssignTaskFragment())
            myFrag.commit()
        }


        return view
    }
}