package com.example.trackprogress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Database.UserCredentials

class LoginFragment : Fragment() {

    lateinit var edtLoginEmail: EditText
    lateinit var edtLoginPassword: EditText
    lateinit var btnLogin: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var myFrag =  inflater.inflate(R.layout.fragment_login, container, false)
        edtLoginEmail = myFrag.findViewById(R.id.edtLoginEmail)!!
        edtLoginPassword = myFrag.findViewById(R.id.edtLoginPassword)
        btnLogin = myFrag.findViewById(R.id.btnLogin)



        btnLogin.setOnClickListener {
            if(validateInput()){

            }
            else{
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
            }
        }


    return myFrag
    }
    fun validateInput(): Boolean{

        if(edtLoginEmail.text.toString().trim() == "" && edtLoginPassword.text.toString().trim() == ""){
            edtLoginEmail.error = "Please enter Email"
            edtLoginPassword.error = "Please enter a password"
            return false
        }

        if(edtLoginPassword.text.toString().trim().length < 8){
            edtLoginPassword.error = "Password length must be greater than 8"
            return false
        }
        return true
    }

    //Continue form here **********************
    fun adminExists(): Boolean{
        return true
    }
    suspend fun createAdmin(){
        val adminUserName = "Admin"
        val adminPassword = BCrypt.withDefaults().hashToString(12,"DefaultAdmin".toCharArray())

        val userCredsDao = AppDatabase.getInstance(requireContext()).userCredsDao()
        userCredsDao.insertUserCredentials(UserCredentials(adminUserName, adminPassword))
    }
}