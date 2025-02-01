package com.example.trackprogress.Admin.Adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Database.Notification
import com.example.trackprogress.Database.NotificationType
import com.example.trackprogress.Database.RaiseQuery
import com.example.trackprogress.Database.Status
import com.example.trackprogress.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date

class AdminQueryAdapter(var ctx: Context, var res: Int, var list: List<RaiseQuery>): ArrayAdapter<RaiseQuery>(ctx,res,list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(ctx)
        val view: View = layoutInflater.inflate(res, null)

        val txtUserQuery: TextView = view.findViewById(R.id.txtUserQuery)
        val btnReply: Button = view.findViewById(R.id.btnReply)
        val data: RaiseQuery = list[position]

        txtUserQuery.text = list[position].userQuery
        val id = data.id
        val userId = data.userId
        val userQuery = data.userQuery
        val reply = data.adminReply
        val status = data.status.toString()
        val raiseQueryDAO = AppDatabase.getInstance(ctx).raiseQueryDao()

        btnReply.setOnClickListener {
            if(status == "PENDING"){
                val alertDialog = AlertDialog.Builder(ctx)
                val edtText = EditText(ctx)
                edtText.hint = "Reply"
                edtText.maxLines = 10

                alertDialog.setView(edtText)

                alertDialog.setTitle("Reply")
                alertDialog.setPositiveButton("Reply"){dialog,_ ->
                    val adminReply = edtText.text.toString()
                    if(adminReply != ""){
                        GlobalScope.launch {
                            raiseQueryDAO.update(RaiseQuery(id, userId, userQuery, adminReply, Status.COMPLETED))
                            val notificationDao = AppDatabase.getInstance(ctx).notificationDao()
                            val notification = Notification(
                                userId = userId,
                                title = "Query Replied",
                                message = "Your query has received a response: $adminReply",
                                timestamp = Date(),
                                type = NotificationType.QUERY_REPLY
                            )
                            notificationDao.insertNotification(notification)

                        }
                        dialog.dismiss()
                    }else{
                        Toast.makeText(ctx,"Reply to the query!",Toast.LENGTH_SHORT).show()
                    }
                }
                alertDialog.setNegativeButton("Cancel"){dialog,_ ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }else if(status == "COMPLETED"){
                val alertDialog = AlertDialog.Builder(ctx)
                    .setTitle("Already Replied!")
                    .setMessage("Reply: \n$reply")
                    .setPositiveButton("OK"){dialog,_ ->
                    dialog.dismiss()
                    }.create()

                alertDialog.show()
            }
        }

        return view
    }
}