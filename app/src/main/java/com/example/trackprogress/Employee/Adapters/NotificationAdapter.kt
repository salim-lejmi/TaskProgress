package com.example.trackprogress.Employee.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trackprogress.Database.Notification
import com.example.trackprogress.R
import java.text.SimpleDateFormat
import java.util.*

class NotificationAdapter(
    private val notifications: List<Notification>,
    private val onNotificationClicked: (Long) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.txtNotificationTitle)
        val messageText: TextView = view.findViewById(R.id.txtNotificationMessage)
        val timeText: TextView = view.findViewById(R.id.txtNotificationTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        holder.titleText.text = notification.title
        holder.messageText.text = notification.message

        val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        holder.timeText.text = dateFormat.format(notification.timestamp)

        holder.itemView.setOnClickListener {
            onNotificationClicked(notification.id)
        }

        // Set background based on read status
        holder.itemView.setBackgroundResource(
            if (notification.isRead) R.color.white else R.color.unread_notification
        )
    }

    override fun getItemCount() = notifications.size
}

