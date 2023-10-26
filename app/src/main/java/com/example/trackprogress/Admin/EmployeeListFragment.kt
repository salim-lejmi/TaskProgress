package com.example.trackprogress.Admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.get
import com.example.trackprogress.Admin.Adapters.MyAdapter
import com.example.trackprogress.Admin.Functionalities.EmployeeOptionsFragment
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.R

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

        lstEmployee.setOnItemClickListener { parent, view, position, id ->
            val view = parent.get(position)
            val id = view.findViewById<TextView>(R.id.txtEmpId).text.toString().toLong()
            val name = view.findViewById<TextView>(R.id.txtEmpName).text.toString()
            val bundle = Bundle()
            bundle.putLong("ID", id)
            bundle.putString("name",name)

            val fragment = EmployeeOptionsFragment()
            fragment.arguments = bundle

            var myFrag = requireActivity().supportFragmentManager.beginTransaction()
            myFrag.replace(R.id.adminFrame, fragment)
            myFrag.commit()

        }

        return view
    }


}