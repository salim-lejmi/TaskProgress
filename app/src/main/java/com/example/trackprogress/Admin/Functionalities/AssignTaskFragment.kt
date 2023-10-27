package com.example.trackprogress.Admin.Functionalities

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Database.Status
import com.example.trackprogress.Database.Task
import com.example.trackprogress.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AssignTaskFragment : Fragment() {
    lateinit var edtTaskTitle: EditText
    lateinit var edtTaskDueDate: EditText
    lateinit var edtTaskDescription: EditText
    lateinit var btnAssign: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_assign_task, container, false)
        edtTaskTitle = view.findViewById(R.id.edtTaskTitle)
        edtTaskDueDate = view.findViewById(R.id.edtTaskDueDate)
        edtTaskDescription = view.findViewById(R.id.edtTaskDescription)
        btnAssign = view.findViewById(R.id.btnAssign)

        val name = arguments?.getString("name")
        val id = arguments?.getLong("ID")

        edtTaskDueDate.setOnClickListener {
            showDatePicker(edtTaskDueDate)
        }
        btnAssign.setOnClickListener {
            if(edtTaskTitle.text.toString() != "" &&
                edtTaskDescription.text.toString() != "" &&
                edtTaskDueDate.text.toString() != ""){
                val title = edtTaskTitle.text.toString()
                val dueDate = stringToDate(edtTaskDueDate.text.toString())
                val description = edtTaskDescription.text.toString()
                if (dueDate != null && id != null) {
                    GlobalScope.launch {
                        assignTask(id,title,dueDate,description)
                    }
                }
                edtTaskTitle.setText("")
                edtTaskDueDate.setText("")
                edtTaskDescription.setText("")


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

    suspend fun assignTask(userID: Long, title: String, dueDate: Date, description: String){
        val taskDAO = AppDatabase.getInstance(requireContext()).taskDao()
        taskDAO.insertTask(Task(0,userID,title,description,dueDate,Status.PENDING))
    }
}