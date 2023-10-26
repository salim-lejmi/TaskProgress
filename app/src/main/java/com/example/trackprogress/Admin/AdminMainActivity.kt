package com.example.trackprogress.Admin

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.trackprogress.AuthActivity
import com.example.trackprogress.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminMainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var bottomAdminNavMenu: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)
        bottomAdminNavMenu = findViewById(R.id.bottomAdminNavMenu)
        sharedPreferences = this.getSharedPreferences(getString(R.string.SharedPref), MODE_PRIVATE)
        val uname = sharedPreferences.getString(getString(R.string.Cred_ID),"")
        val usertype = sharedPreferences.getString(getString(R.string.User_Type),getString(R.string.employee))
        getSupportActionBar()?.hide()
        loadFrag(AddEmployeeFragment())

        bottomAdminNavMenu.setOnItemSelectedListener {
            when(it.itemId){
                R.id.addEmployee -> { loadFrag(AddEmployeeFragment()) }
                R.id.employeeList -> {loadFrag(EmployeeListFragment())}
                R.id.logOut -> {
                    val editor = sharedPreferences.edit()
                    //editor.putString(getString(R.string.Cred_ID),"")
                    //editor.putString(getString(R.string.User_Type),"")
                    editor.putBoolean(getString(R.string.Cred_Pref),false)
                    editor.commit()
                    val intent = Intent(this,AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }


    }

    fun loadFrag(frag: Fragment){
        var myFrag = supportFragmentManager.beginTransaction()
        myFrag.replace(R.id.adminFrame,frag)
        myFrag.commit()
    }

}
