package com.example.trackprogress.Admin.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Database.Task
import com.example.trackprogress.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import kotlin.math.log

class TaskDetailsAdapter(var ctx: Context, var res: Int, var list: List<Task>): ArrayAdapter<Task>(ctx,res,list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(ctx)
        val view: View = layoutInflater.inflate(res, null)
        val txtTaskTitleCustom: TextView = view.findViewById(R.id.txtTaskTitleCustom)
        val txtCompletionDateCustom: TextView = view.findViewById(R.id.txtCompletionDateCustom)
        val txtCompletionTemplate: TextView = view.findViewById(R.id.txtCompletionTemplate)
        val cstTaskDetailCustom: ConstraintLayout = view.findViewById(R.id.cstTaskDetailCustom)
        val data: Task = list[position]
        val taskId = data.id
        val status = data.status.toString()
        txtTaskTitleCustom.text = data.title
        val taskCompletionDAO = AppDatabase.getInstance(ctx).taskCompletionDao()

        if(status == "PENDING"){
            cstTaskDetailCustom.setBackgroundResource(R.drawable.gradient_pending)
            txtCompletionDateCustom.visibility = View.GONE
            txtCompletionTemplate.visibility = View.GONE
        }else if(status == "COMPLETED"){
            cstTaskDetailCustom.setBackgroundResource(R.drawable.gradient_completed)
            GlobalScope.launch(Dispatchers.Main) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val completionDate = dateFormat.format(taskCompletionDAO.getCompletionDateByTaskId(taskId))
                txtCompletionDateCustom.text = completionDate
            }
        }
        return view
    }
}