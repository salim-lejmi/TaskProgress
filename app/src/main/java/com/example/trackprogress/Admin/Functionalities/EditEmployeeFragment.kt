package com.example.trackprogress.Admin.Functionalities

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EditEmployeeFragment : Fragment() {

    lateinit var txtEditId: TextView
    lateinit var txtEditEmail: TextView
    lateinit var edtEditName: EditText
    lateinit var edtEditDOB: EditText
    lateinit var edtEditDOJ: EditText
    lateinit var spinnerEditDepartment: Spinner
    lateinit var edtEditDesignation: EditText
    lateinit var edtEditSalary: EditText
    lateinit var edtEditLeaves: EditText
    lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_employee, container, false)
        txtEditId = view.findViewById(R.id.txtEditID)
        txtEditEmail = view.findViewById(R.id.txtEditEmail)
        edtEditName = view.findViewById(R.id.edtEditName)
        edtEditDOB = view.findViewById(R.id.edtEditDOB)
        edtEditDOJ = view.findViewById(R.id.edtEditDOJ)
        spinnerEditDepartment = view.findViewById(R.id.spinnerEditDepartment)
        edtEditDesignation = view.findViewById(R.id.edtEditDesignation)
        edtEditSalary = view.findViewById(R.id.edtEditSalary)
        edtEditLeaves = view.findViewById(R.id.edtEditLeaves)
        btnUpdate = view.findViewById(R.id.btnUpdate)

        val id = arguments?.getLong("ID")!!
        val name = arguments?.getString("name")!!
        val userDAO = AppDatabase.getInstance(requireContext()).userDao()
        val employeeDAO = AppDatabase.getInstance(requireContext()).employeeDao()

        GlobalScope.launch {
            val user = userDAO.getUserById(id)
            val emp = employeeDAO.getEmployeeByID(id)
            val email = user?.email!!
            if(email != null){
                txtEditEmail.setText(email)
            }

            val dob = emp?.dateOfBirth
            val doj = emp?.joiningDate
            val department = emp?.department
            val designation = emp?.designation
            val salary = emp?.salary
            val leaves = emp?.leaveBalance

            edtEditDOB.setText(dob.toString())
            edtEditDOJ.setText(doj.toString())
            edtEditDesignation.setText(designation)
            edtEditSalary.setText(salary.toString())
            edtEditLeaves.setText(leaves.toString())
        }
        txtEditId.setText("$id")
        edtEditName.setText(name)



        edtEditDOJ.setOnClickListener {
            showDatePicker(edtEditDOB)
        }
        edtEditDOJ.setOnClickListener {
            showDatePicker(edtEditDOJ)
        }

        return view
    }

    fun showDatePicker(edtText: EditText){
        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        var datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val date = "$year-${month + 1}-$dayOfMonth"
                edtText.setText(date)
            },year,month,day)
        datePickerDialog.show()
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