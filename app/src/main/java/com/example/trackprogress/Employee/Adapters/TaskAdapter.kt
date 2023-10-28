package com.example.trackprogress.Employee.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.trackprogress.Database.Task
import com.example.trackprogress.R

class TaskAdapter(var ctx: Context, var res: Int, var list: List<Task>): ArrayAdapter<Task>(ctx, res, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(ctx)
        val view: View = layoutInflater.inflate(res, null)

        val txtTaskId: TextView = view.findViewById(R.id.txtTaskID)
        val txtTaskTitle: TextView = view.findViewById(R.id.txtTaskTitle)
        val txtTaskStatus: TextView = view.findViewById(R.id.txtTaskStatus)

        val data: Task = list[position]
        txtTaskId.text = data.id.toString()
        txtTaskTitle.text = data.title
        txtTaskStatus.text = data.status.toString()

        return view
    }
}