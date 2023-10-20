package com.example.trackprogress

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    lateinit var txtWish : TextView
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtWish = findViewById(R.id.txtWish)
        sharedPreferences = this.getSharedPreferences(getString(R.string.SharedPref), MODE_PRIVATE)
        val uname = sharedPreferences.getString(getString(R.string.Cred_ID),"")
        val usertype = sharedPreferences.getString(getString(R.string.User_Type),"")
            txtWish.setText("WELCOME, $uname ($usertype)")


    }

}
