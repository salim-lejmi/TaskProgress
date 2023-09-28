package com.example.trackprogress

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        getSupportActionBar()?.hide()

        sharedPreferences = this.getSharedPreferences("Shared_Prefs", MODE_PRIVATE)
        val isLogIn = sharedPreferences.getBoolean("Cred_Pref",false)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                if(isLogIn){
                    var intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    /*
                    var intent = Intent(this,)
                    startActivity(intent)
                    */
                }
                finish()
            },
            3000
        )
    }
}