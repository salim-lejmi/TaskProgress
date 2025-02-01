package com.example.trackprogress.Employee

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.trackprogress.AuthActivity
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Employee.Functionalities.NotificationsFragment
import com.example.trackprogress.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

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
        val uname = sharedPreferences.getString(getString(R.string.Cred_ID),"")!!
        getSupportActionBar()?.hide()
        loadFrag(EmployeeFragment())

        // Add this: Set up notification icon observer
        lifecycleScope.launch {
            val userDAO = AppDatabase.getInstance(this@EmployeeMainActivity).userDao()
            val user = userDAO.getUserByEmail(uname)
            user?.let {
                val notificationDao = AppDatabase.getInstance(this@EmployeeMainActivity).notificationDao()
                notificationDao.getUnreadNotificationCount(user.id!!).observe(this@EmployeeMainActivity) { count ->
                    val menuItem = bottomEmployeeNavBar.menu.findItem(R.id.employeeNotifications)
                    menuItem.setIcon(
                        if (count > 0) R.drawable.ic_notification_unread
                        else R.drawable.ic_notifications
                    )
                }
            }
        }

        bottomEmployeeNavBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.employeeHome -> { loadFrag(EmployeeFragment()) }
                R.id.employeeNotifications -> {
                    val uname = sharedPreferences.getString(getString(R.string.Cred_ID), "")!!
                    lifecycleScope.launch {
                        val userDAO = AppDatabase.getInstance(this@EmployeeMainActivity).userDao()
                        val user = userDAO.getUserByEmail(uname)
                        val fragment = NotificationsFragment()
                        val bundle = Bundle()
                        bundle.putLong("ID", user?.id ?: 0)
                        fragment.arguments = bundle
                        loadFrag(fragment)
                    }
                }
                R.id.employeeLogOut -> {
                    val editor = sharedPreferences.edit()
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
