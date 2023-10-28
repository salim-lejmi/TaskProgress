package com.example.trackprogress.Employee.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Database.PendingLeaves
import com.example.trackprogress.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class LeaveAdapter(var ctx: Context, var res: Int,var list: List<PendingLeaves>): ArrayAdapter<PendingLeaves>(ctx, res, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(ctx)
        val view: View = layoutInflater.inflate(res, null)

        val txtFromDate1 : TextView = view.findViewById(R.id.txtFromDate1)
        val txtAppliedDate1 : TextView = view.findViewById(R.id.txtAppliedDate1)
        val txtCount1: TextView = view.findViewById(R.id.txtCount1)
        val btncancel: Button = view.findViewById(R.id.btnCancel)

        val data: PendingLeaves = list[position]
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        txtFromDate1.text =  dateFormat.format(data.fromDate.toString())
        txtAppliedDate1.text = dateFormat.format(data.appliedDate.toString())
        txtCount1.text = data.count.toString()
        val leaveId = data.id
        val pendingLeavesDAO = AppDatabase.getInstance(ctx).pendingLeavesDao()
        btncancel.setOnClickListener {
            GlobalScope.launch {
                pendingLeavesDAO.deleteLeaveByLeaveId(leaveId)
            }
        }
        return view
    }
}