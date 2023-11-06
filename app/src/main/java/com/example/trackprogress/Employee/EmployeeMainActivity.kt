package com.example.trackprogress.Employee

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.trackprogress.AuthActivity
import com.example.trackprogress.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class EmployeeMainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var bottomEmployeeNavBar: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_employee_main)
        bottomEmployeeNavBar = findViewById(R.id.bottomEmployeeNavMenu)
        sharedPreferences = this.getSharedPreferences(getString(R.string.SharedPref), MODE_PRIVATE)
        val uname = sharedPreferences.getString(getString(R.string.Cred_ID),"")
        getSupportActionBar()?.hide()
        loadFrag(EmployeeFragment())

        bottomEmployeeNavBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.employeeHome -> { loadFrag(EmployeeFragment()) }
                R.id.employeeLogOut -> {
                    val editor = sharedPreferences.edit()
                    //editor.putString(getString(R.string.Cred_ID),"")
                    //editor.putString(getString(R.string.User_Type),"")
                    editor.putBoolean(getString(R.string.Cred_Pref),false)
                    editor.commit()
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }


    }

    fun loadFrag(frag: Fragment){
        var myFrag = supportFragmentManager.beginTransaction()
        myFrag.replace(R.id.employeeFrame,frag)
        myFrag.commit()
    }
}