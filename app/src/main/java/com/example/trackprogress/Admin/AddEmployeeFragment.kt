package com.example.trackprogress.Admin

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Database.Employee
import com.example.trackprogress.Database.User
import com.example.trackprogress.Database.UserCredentials
import com.example.trackprogress.Database.UserType
import com.example.trackprogress.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddEmployeeFragment : Fragment() {

    lateinit var edtID: EditText
    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtPassword1: EditText
    lateinit var edtDOB: EditText
    lateinit var edtDOJ: EditText
    lateinit var spinnerDepartment: Spinner
    lateinit var edtDesignation: EditText
    lateinit var edtSalary: EditText
    lateinit var edtLeaves: EditText
    lateinit var btnAddEmp: Button

    var id = 0L
    var name = ""
    var email = ""
    private var password = ""
    var selectedDepartment = ""
    var designation = ""
    var salary = 0.0
    var leaves = 0

   var departmentList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_employee, container, false)

        edtID = view.findViewById(R.id.edtID)
        edtName = view.findViewById(R.id.edtName)
        edtEmail = view.findViewById(R.id.edtEmail)
        edtPassword1 = view.findViewById(R.id.edtPassword1)
        edtDOB = view.findViewById(R.id.edtDOB)
        edtDOJ = view.findViewById(R.id.edtDOJ)
        spinnerDepartment = view.findViewById(R.id.spinnerDepartment)
        edtDesignation = view.findViewById(R.id.edtDesignation)
        edtSalary = view.findViewById(R.id.edtSalary)
        edtLeaves = view.findViewById(R.id.edtLeaves)
        btnAddEmp = view.findViewById(R.id.btnAddEmp)

        departmentList.add("Select a Department")
        departmentList.add("Administration")
        departmentList.add("Engineering")
        departmentList.add("Finance")
        departmentList.add("R&D")
        departmentList.add("InfoTech")

        val arrAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, departmentList) }
        arrAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)

        spinnerDepartment.adapter = arrAdapter

        spinnerDepartment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position == 0){
                    spinnerDepartment.setSelection(0, false)
                    Toast.makeText(context,"Select a valid value",Toast.LENGTH_SHORT).show()
                }else{
                    selectedDepartment = departmentList[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        edtDOB.setOnClickListener {
            showDatePicker(edtDOB)
        }
        edtDOJ.setOnClickListener {
            showDatePicker(edtDOJ)
        }

        btnAddEmp.setOnClickListener {

            if(edtName.text.toString() !="" &&
                edtName.text.toString() != "" &&
                edtEmail.text.toString() != "" &&
                edtPassword1.text.toString() != "" &&
                edtDOB.text.toString() != "" &&
                edtDOJ.text.toString() != "" &&
                edtDesignation.text.toString() != "" &&
                edtSalary.text.toString() != "" &&
                edtLeaves.text.toString() != "" &&
                (selectedDepartment != "" ||
                        selectedDepartment != "Select a Department")){

                id = edtID.text.toString().toLong()
                name = edtName.text.toString()
                email = edtEmail.text.toString()
                password = BCrypt.withDefaults().hashToString(12,edtPassword1.text.toString().toCharArray())
                val dob = stringToDate(edtDOB.text.toString())
                val doj = stringToDate(edtDOJ.text.toString())
                designation = edtDesignation.text.toString()
                salary = edtSalary.text.toString().toDouble()
                leaves = edtLeaves.text.toString().toInt()

                edtID.setText("")
                edtName.setText("")
                edtEmail.setText("")
                edtPassword1.setText("")
                edtDOB.setText("")
                edtDOJ.setText("")
                spinnerDepartment.setSelection(0)
                edtDesignation.setText("")
                edtSalary.setText("")
                edtLeaves.setText("")

                GlobalScope.launch {
                    addUserCreds(email,password)
                    addUser(id,name,email)
                    if(dob != null && doj != null) {
                        addEmp(id, selectedDepartment, doj!!, dob!!, designation, salary, leaves)
                    }else{
                        Toast.makeText(context,"Invalid Date Format",Toast.LENGTH_SHORT).show()
                    }
                }

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

    suspend fun addUserCreds(uname: String, pswd: String){
        val userCredsDao = AppDatabase.getInstance(requireContext()).userCredsDao()
        userCredsDao.insertUserCredentials(UserCredentials(uname,pswd))
    }

    suspend fun addUser(id: Long, name: String, email: String){
        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        userDao.insertUser(User(id,name,email,UserType.EMPLOYEE))
    }

    suspend fun addEmp(id: Long, department: String, doj: Date, dob:Date, designation: String, salary: Double, leaveBalance: Int){
        val employeeDAO = AppDatabase.getInstance(requireContext()).employeeDao()
        employeeDAO.insert(Employee(id,department,doj,dob,designation,salary,leaveBalance))
    }
}