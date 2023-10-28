package com.example.trackprogress.Admin.Functionalities

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Database.Employee
import com.example.trackprogress.Database.User
import com.example.trackprogress.Database.UserType
import com.example.trackprogress.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
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

    var selectedEditDepartment = ""
    var departmentEditList = ArrayList<String>()
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

        departmentEditList.add("Select a Department")
        departmentEditList.add("Administration")
        departmentEditList.add("Engineering")
        departmentEditList.add("Finance")
        departmentEditList.add("R&D")
        departmentEditList.add("InfoTech")

        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,departmentEditList)
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerEditDepartment.adapter = adapter

        val id = arguments?.getLong("ID")!!
        val name = arguments?.getString("name")!!
        val userDAO = AppDatabase.getInstance(requireContext()).userDao()
        val employeeDAO = AppDatabase.getInstance(requireContext()).employeeDao()

        lifecycleScope.launch{
            val user = userDAO.getUserById(id)
            val emp = employeeDAO.getEmployeeByID(id)
            val email = user?.email!!
            if(email != null){
                txtEditEmail.setText(email)
            }

            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val dob = dateFormat.format(emp?.dateOfBirth)
            val doj = dateFormat.format(emp?.joiningDate)
            val department = emp?.department
            val designation = emp?.designation
            val salary = emp?.salary
            val leaves = emp?.leaveBalance

            val position = adapter.getPosition(department)

            edtEditDOB.setText(dob)
            edtEditDOJ.setText(doj)
            spinnerEditDepartment.setSelection(position)
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
        spinnerEditDepartment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position == 0){
                    spinnerEditDepartment.setSelection(0,false)
                }else{
                    selectedEditDepartment = departmentEditList[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        btnUpdate.setOnClickListener {
            if(edtEditName.text.toString() != "" &&
                edtEditDOB.text.toString() != "" &&
                edtEditDOJ.text.toString() != "" &&
                edtEditDesignation.text.toString() != "" &&
                edtEditSalary.text.toString() != "" &&
                edtEditLeaves.text.toString() != "" &&
                (selectedEditDepartment != "" ||
                        selectedEditDepartment != "Select a Department")
                ){
                val name = edtEditName.text.toString()
                val dob = stringToDate(edtEditDOB.text.toString())!!
                val doj = stringToDate(edtEditDOJ.text.toString())!!
                val designation = edtEditDesignation.text.toString()
                val salary = edtEditSalary.text.toString().toDouble()
                val leaves = edtEditLeaves.text.toString().toInt()

                GlobalScope.launch {
                    updateUser(id, name, txtEditEmail.text.toString())
                    updateEmployee(id,selectedEditDepartment,doj,dob,designation,salary, leaves)
                }
                Toast.makeText(context,"Employee Updated",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"Fill all fields",Toast.LENGTH_SHORT).show()
            }
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

    suspend fun updateUser(id: Long, name: String, email: String){
        val userDAO = AppDatabase.getInstance(requireContext()).userDao()
        userDAO.updateUser(User(id, name, email, UserType.EMPLOYEE))
    }

    suspend fun updateEmployee(userId: Long, department: String, doj: Date, dob: Date, designation: String, salary: Double, leaves: Int){
        val employeeDAO = AppDatabase.getInstance(requireContext()).employeeDao()
        employeeDAO.update(Employee(userId, department, doj, dob, designation, salary, leaves))
    }
}