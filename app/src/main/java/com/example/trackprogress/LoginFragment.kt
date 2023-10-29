package com.example.trackprogress

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.trackprogress.Admin.AdminMainActivity
import com.example.trackprogress.Database.AppDatabase
import com.example.trackprogress.Database.User
import com.example.trackprogress.Database.UserCredentials
import com.example.trackprogress.Database.UserType
import com.example.trackprogress.Employee.ChangePasswordFragment
import com.example.trackprogress.Employee.EmployeeMainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    lateinit var edtLoginEmail: EditText
    lateinit var edtLoginPassword: EditText
    lateinit var txtChangePassword: TextView
    lateinit var btnLogin: Button
    lateinit var sharedPreferences: SharedPreferences


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
        txtChangePassword = myFrag.findViewById(R.id.txtChangePassword)
        btnLogin = myFrag.findViewById(R.id.btnLogin)

        sharedPreferences = requireContext().getSharedPreferences(getString(R.string.SharedPref),
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()

        txtChangePassword.setOnClickListener {
            val frag = requireActivity().supportFragmentManager.beginTransaction()
            frag.replace(R.id.authFrame,ChangePasswordFragment())
            frag.commit()

        }

        GlobalScope.launch {
            if(!adminExists()){
                createAdmin()
            }
        }

        btnLogin.setOnClickListener {
            if(validateInput()){
                val uname = edtLoginEmail.text.toString()

                val userCredsDao = AppDatabase.getInstance(requireContext()).userCredsDao()
                 CoroutineScope(Dispatchers.IO).launch {
                     val userCredentials = userCredsDao.getCredByUsername(uname)

                     if(userCredentials != null){
                         if(BCrypt.verifyer().verify(edtLoginPassword.text.toString().toCharArray(), userCredentials.password).verified){
                             val userDao = AppDatabase.getInstance(requireContext()).userDao()
                             val user = userDao.getUserByEmail(uname)
                             editor.putBoolean(getString(R.string.Cred_Pref),true)
                             editor.putString(getString(R.string.Cred_ID),uname)
                             if(user?.userType == UserType.ADMIN){
                                 editor.putString(getString(R.string.User_Type),getString(R.string.admin))
                                 val intent = Intent(context, AdminMainActivity::class.java)
                                 startActivity(intent)
                                 activity?.finish()
                             }else{
                                 editor.putString(getString(R.string.User_Type),getString(R.string.employee))
                                 val intent = Intent(context, EmployeeMainActivity::class.java)
                                 startActivity(intent)
                                 activity?.finish()
                             }
                             editor.commit()
                         }else{
                             activity?.runOnUiThread{
                                 Toast.makeText(requireContext(),"Invalid Password",Toast.LENGTH_SHORT).show()
                             }
                         }
                     }else{
                         activity?.runOnUiThread{
                             Toast.makeText(requireContext(),"User not found",Toast.LENGTH_SHORT).show()
                         }
                     }
                 }
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

    suspend fun adminExists(): Boolean{
        val userCredsDao = AppDatabase.getInstance(requireContext()).userCredsDao()
        val adminCount = userCredsDao.adminExists()
        if(adminCount > 0){
            return true
        }
        return false
    }
    suspend fun createAdmin(){
        val adminUserName = "Admin"
        val adminPassword = BCrypt.withDefaults().hashToString(12,"DefaultAdmin".toCharArray())

        val userCredsDao = AppDatabase.getInstance(requireContext()).userCredsDao()
        userCredsDao.insertUserCredentials(UserCredentials(adminUserName, adminPassword))

        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        userDao.insertUser(User(1000,"admin","Admin",UserType.ADMIN))
    }
}