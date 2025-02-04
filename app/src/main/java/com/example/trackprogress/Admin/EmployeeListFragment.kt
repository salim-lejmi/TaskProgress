package com.example.trackprogress.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.R
import com.google.android.material.appbar.MaterialToolbar
import com.example.trackprogress.Admin.Adapters.EmployeeAdapter
import com.example.trackprogress.Admin.Functionalities.EmployeeOptionsFragment

class EmployeeListFragment : Fragment() {

    private lateinit var recyclerEmployees: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the new fragment layout with Material Toolbar and RecyclerView
        val view = inflater.inflate(R.layout.fragment_employee_list, container, false)

        // Initialize RecyclerView and set a LinearLayoutManager
        recyclerEmployees = view.findViewById(R.id.recyclerEmployees)
        recyclerEmployees.layoutManager = LinearLayoutManager(requireContext())

        // Setup toolbar navigation (if you want a back button, for example)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        // Observe LiveData from your DAO (assuming getUser() returns LiveData<List<User>>)
        val userDAO = AppDatabase.getInstance(requireContext()).userDao()
        userDAO.getUser().observe(viewLifecycleOwner) { userList ->
            // Set the adapter with an onItemClick lambda
            recyclerEmployees.adapter = EmployeeAdapter(userList) { selectedUser ->
                // When an employee is clicked, pass the details to EmployeeOptionsFragment
                val bundle = Bundle().apply {
                    putLong("ID", selectedUser.id ?: 0L)
                    putString("name", selectedUser.name)
                }
                val fragment = EmployeeOptionsFragment()
                fragment.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.adminFrame, fragment)
                    .commit()
            }
        }

        return view
    }
}
