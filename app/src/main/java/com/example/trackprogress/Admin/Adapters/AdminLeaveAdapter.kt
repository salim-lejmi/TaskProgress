package com.example.trackprogress.Admin.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.trackprogress.Database.PendingLeaves

class AdminLeaveAdapter(var ctx: Context, var res: Int, var list: List<PendingLeaves>): ArrayAdapter<PendingLeaves>(ctx,res,list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(ctx)
        val view: View = layoutInflater.inflate(res, null)

        val data: PendingLeaves = list[position]

        return view
    }
}