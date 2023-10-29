package com.example.trackprogress.Admin.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Database.PendingLeaves
import com.example.trackprogress.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class AdminLeaveAdapter(var ctx: Context, var res: Int, var list: List<PendingLeaves>): ArrayAdapter<PendingLeaves>(ctx,res,list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(ctx)
        val view: View = layoutInflater.inflate(res, null)
        val txtFrom: TextView = view.findViewById(R.id.txtFrom)
        val txtOn: TextView = view.findViewById(R.id.txtOn)
        val txtCount: TextView = view.findViewById(R.id.txtCount)
        val btnApprove: Button = view.findViewById(R.id.btnApprove)
        val data: PendingLeaves = list[position]
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        txtFrom.text = dateFormat.format(data.fromDate)
        txtOn.text = dateFormat.format(data.appliedDate)
        txtCount.text = data.count.toString()

        val employeeDAO = AppDatabase.getInstance(ctx).employeeDao()
        val pendingLeavesDAO = AppDatabase.getInstance(ctx).pendingLeavesDao()
        btnApprove.setOnClickListener {
            val leaveId = data.id
            val id = data.userId
            val count = data.count
            GlobalScope.launch {
                val balance = employeeDAO.getLeaveBalance(id)!! - count
                if(balance < 0){
                    Toast.makeText(ctx,"Low leave balance!",Toast.LENGTH_SHORT).show()
                }else{
                    pendingLeavesDAO.deleteLeaveByLeaveId(leaveId)
                    employeeDAO.updateLeaves(id, count)
                }
            }
        }
        return view
    }
}