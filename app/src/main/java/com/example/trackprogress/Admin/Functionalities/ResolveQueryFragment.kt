package com.example.trackprogress.Admin.Functionalities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.trackprogress.Admin.Adapters.AdminQueryAdapter
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.R

class ResolveQueryFragment : Fragment() {
    lateinit var lstAdminQuery: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_resolve_query, container, false)
        lstAdminQuery = view.findViewById(R.id.lstAdminQuery)
        val id = arguments?.getLong("ID")!!

        val raiseQueryDAO = AppDatabase.getInstance(requireContext()).raiseQueryDao()
        raiseQueryDAO.displayQuery(id).observe(viewLifecycleOwner){
            val adapter = AdminQueryAdapter(requireContext(), R.layout.custom_admin_query_view,it)
            lstAdminQuery.adapter = adapter
        }
        return view
    }
}