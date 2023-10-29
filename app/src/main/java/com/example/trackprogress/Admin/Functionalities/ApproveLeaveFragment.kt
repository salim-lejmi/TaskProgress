package com.example.trackprogress.Admin.Functionalities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.trackprogress.Admin.Adapters.AdminLeaveAdapter
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.R

class ApproveLeaveFragment : Fragment() {

    lateinit var lstApproveLeaves: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_approve_leave, container, false)
        lstApproveLeaves = view.findViewById(R.id.lstApproveLeaves)
        val id = arguments?.getLong("ID")!!
        val pendingLeavesDAO = AppDatabase.getInstance(requireContext()).pendingLeavesDao()
        pendingLeavesDAO.displayLeaves(id).observe(viewLifecycleOwner){
            val adapter = AdminLeaveAdapter(requireContext(),R.layout.custom_admin_leave_view,it)
            lstApproveLeaves.adapter = adapter
        }

        return view
    }
}