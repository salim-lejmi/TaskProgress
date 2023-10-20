package com.example.trackprogress.Admin

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.trackprogress.Employee.EmployeeFragment
import com.example.trackprogress.R

class AdminMainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)
        sharedPreferences = this.getSharedPreferences(getString(R.string.SharedPref), MODE_PRIVATE)
        val uname = sharedPreferences.getString(getString(R.string.Cred_ID),"")
        val usertype = sharedPreferences.getString(getString(R.string.User_Type),getString(R.string.employee))
        getSupportActionBar()?.hide()

        loadFrag(AdminFragment())


    }

    fun loadFrag(frag: Fragment){
        var myFrag = supportFragmentManager.beginTransaction()
        myFrag.replace(R.id.adminFrame,frag)
        myFrag.commit()
    }

}
