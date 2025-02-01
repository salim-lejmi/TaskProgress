package com.example.trackprogress.Employee.Functionalities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Employee.Adapters.NotificationAdapter
import com.example.trackprogress.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotificationsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)
        recyclerView = view.findViewById(R.id.recyclerNotifications)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val userId = arguments?.getLong("ID") ?: return view

        val notificationDao = AppDatabase.getInstance(requireContext()).notificationDao()

        notificationDao.getNotificationsForUser(userId).observe(viewLifecycleOwner) { notifications ->
            adapter = NotificationAdapter(notifications) { notificationId ->
                GlobalScope.launch {
                    notificationDao.markNotificationAsRead(notificationId)
                }
            }
            recyclerView.adapter = adapter
        }

        return view
    }
}

