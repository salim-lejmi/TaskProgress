package com.example.trackprogress.Admin.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trackprogress.Database.User
import com.example.trackprogress.R

class EmployeeAdapter(
    private val employees: List<User>,
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    inner class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtEmpId: TextView = itemView.findViewById(R.id.txtEmpId)
        val txtEmpName: TextView = itemView.findViewById(R.id.txtEmpName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_employee, parent, false)
        return EmployeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employees[position]
        holder.txtEmpId.text = employee.id?.toString() ?: ""
        holder.txtEmpName.text = employee.name

        // Handle item click events
        holder.itemView.setOnClickListener {
            onItemClick(employee)
        }
    }

    override fun getItemCount(): Int = employees.size
}
