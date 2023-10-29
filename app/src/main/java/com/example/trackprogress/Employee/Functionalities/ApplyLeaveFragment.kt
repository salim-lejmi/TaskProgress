package com.example.trackprogress.Employee.Functionalities

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Database.PendingLeaves
import com.example.trackprogress.Employee.Adapters.LeaveAdapter
import com.example.trackprogress.R
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ApplyLeaveFragment : Fragment() {

    lateinit var txtLeaveBalance: TextView
    lateinit var edtFromDate: EditText
    lateinit var edtLeaveCount: EditText
    lateinit var btnApply: Button
    lateinit var txtPendingTitle: TextView
    lateinit var lstPendingLeaves: ListView

    lateinit var adapter: LeaveAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_apply_leave, container, false)
        txtLeaveBalance = view.findViewById(R.id.txtLeavebalance)
        edtFromDate = view.findViewById(R.id.edtFromDate)
        edtLeaveCount = view.findViewById(R.id.edtLeaveCount)
        btnApply = view.findViewById(R.id.btnApply)
        txtPendingTitle = view.findViewById(R.id.txtPendingTitle)
        lstPendingLeaves = view.findViewById(R.id.lstPendingLeave)

        adapter = LeaveAdapter(requireContext(),R.layout.custom_leave_view, ArrayList())
        lstPendingLeaves.adapter = adapter
        val id = arguments?.getLong("ID")!!

        edtFromDate.setOnClickListener {
            showDatePicker(edtFromDate)
        }
        val pendingLeavesDAO = AppDatabase.getInstance(requireContext()).pendingLeavesDao()
        val employeeDAO = AppDatabase.getInstance(requireContext()).employeeDao()
        lifecycleScope.launch {
            val leaveBalance = employeeDAO.getEmployeeByID(id)?.leaveBalance!!
            txtLeaveBalance.setText("$leaveBalance")
        }
        btnApply.setOnClickListener {
            if(txtLeaveBalance.text.toString() != "" &&
                edtFromDate.text.toString() != "" &&
                edtLeaveCount.text.toString() != "" &&
                edtLeaveCount.text.toString().toInt() <= txtLeaveBalance.text.toString().toInt() ){
                val fromDate = stringToDate(edtFromDate.text.toString())!!
                val currentDate = stringToDate(getCurrentDate())!!
                val count = edtLeaveCount.text.toString().toInt()!!
                lifecycleScope.launch{
                    pendingLeavesDAO.insert(PendingLeaves(0,id,fromDate,count,currentDate))
                    getPendingLeaves(id)
                }
            }else{
                Toast.makeText(context,"Wrong task",Toast.LENGTH_SHORT).show()
            }
        }

        getPendingLeaves(id)
        return view
    }

    fun getPendingLeaves(userId: Long){
        val pendingLeavesDAO = AppDatabase.getInstance(requireContext()).pendingLeavesDao()
        pendingLeavesDAO.displayLeaves(userId).observe(viewLifecycleOwner){data ->
            adapter.clear()
            adapter.addAll(data)
            adapter.notifyDataSetChanged()
        }
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

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date(System.currentTimeMillis())
        return dateFormat.format(date)
    }

}