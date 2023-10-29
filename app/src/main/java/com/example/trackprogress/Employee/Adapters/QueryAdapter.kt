package com.example.trackprogress.Employee.Adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.trackprogress.Database.RaiseQuery
import com.example.trackprogress.R

class QueryAdapter(var ctx: Context, var res: Int, var list: List<RaiseQuery>): ArrayAdapter<RaiseQuery>(ctx,res,list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(ctx)
        val view: View = layoutInflater.inflate(res, null)

        val txtQueryCustom: TextView = view.findViewById(R.id.txtQueryCustom)
        val btnQueryStatus: Button = view.findViewById(R.id.btnQueryStatus)

        val data: RaiseQuery = list[position]
        txtQueryCustom.text = data.userQuery
        val status = data.status.toString()
        btnQueryStatus.setOnClickListener {
            if(status == "PENDING"){
                val alertDialog = AlertDialog.Builder(ctx)
                    .setTitle("Query Status: PENDING")
                    .setMessage("This query is pending. There is no reply for it at the moment.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()

                alertDialog.show()
            }else{
                val reply = data.adminReply
                val alertDialog = AlertDialog.Builder(ctx)
                    .setTitle("Query Status: COMPLETED")
                    .setMessage("Reply: $reply")
                    .setPositiveButton("OK"){
                    dialog,_ ->
                    dialog.dismiss()
                }.create()
                alertDialog.show()
            }
        }


        return view
    }
}