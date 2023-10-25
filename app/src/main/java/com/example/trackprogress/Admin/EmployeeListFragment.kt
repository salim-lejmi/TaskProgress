package com.example.trackprogress.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EmployeeListFragment : Fragment() {
    lateinit var lstEmployee: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_employee_list, container, false)
        lstEmployee = view.findViewById(R.id.lstEmployees)
        val userDAO = AppDatabase.getInstance(context).userDao()
        userDAO.getUser().observe(viewLifecycleOwner){
            val adapter = MyAdapter(requireContext(),R.layout.custom_list_view,it)
            lstEmployee.adapter = adapter
        }

        return view
    }


}