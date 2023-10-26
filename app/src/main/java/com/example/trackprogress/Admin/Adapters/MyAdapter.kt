package com.example.trackprogress.Admin.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.trackprogress.Database.User
import com.example.trackprogress.R

class MyAdapter(var ctx: Context,var res: Int,var list: List<User> ): ArrayAdapter<User>(ctx, res, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(ctx)
        val view: View = layoutInflater.inflate(res, null)

        val txtEmpId: TextView = view.findViewById(R.id.txtEmpId)
        val txtEmpName: TextView = view.findViewById(R.id.txtEmpName)
        val data: User = list[position]
        txtEmpId.text = data.id.toString()
        txtEmpName.text = data.name

        return view
    }
}