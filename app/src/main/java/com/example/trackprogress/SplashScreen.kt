package com.example.trackprogress

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView

class SplashScreen : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var imgView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        imgView = findViewById(R.id.imgView)
        val fadeIn = ObjectAnimator.ofFloat(imgView, "alpha", 0f, 1f)
        fadeIn.duration = 4000
        fadeIn.start()

        getSupportActionBar()?.hide()

        sharedPreferences = this.getSharedPreferences(getString(R.string.SharedPref), MODE_PRIVATE)
        val isLogIn = sharedPreferences.getBoolean(getString(R.string.Cred_Pref),false)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                if(isLogIn){
                    var intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    var intent = Intent(this,AuthActivity::class.java)
                    startActivity(intent)


                }
                finish()
            },
            5000
        )
    }
}