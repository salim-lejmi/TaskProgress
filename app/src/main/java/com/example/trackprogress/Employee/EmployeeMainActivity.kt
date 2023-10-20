package com.example.trackprogress.Employee

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.trackprogress.R

class EmployeeMainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_main)
        sharedPreferences = this.getSharedPreferences(getString(R.string.SharedPref), MODE_PRIVATE)
        val uname = sharedPreferences.getString(getString(R.string.Cred_ID),"")
        getSupportActionBar()?.hide()
        loadFrag(EmployeeFragment())
    }

    fun loadFrag(frag: Fragment){
        var myFrag = supportFragmentManager.beginTransaction()
        myFrag.replace(R.id.employeeFrame,frag)
        myFrag.commit()
    }
}