package com.example.trackprogress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_auth)

        getSupportActionBar()?.hide()
        loadFrag(LoginFragment())
    }
    fun loadFrag(frag: Fragment){
        var myFrag = supportFragmentManager.beginTransaction()
        myFrag.replace(R.id.authFrame,frag)
        myFrag.commit()
    }
}