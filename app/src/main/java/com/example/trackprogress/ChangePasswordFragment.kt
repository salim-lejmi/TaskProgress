package com.example.trackprogress

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.trackprogress.Database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangePasswordFragment : Fragment() {

    lateinit var edtEmail1: EditText
    lateinit var edtOldPassword: EditText
    lateinit var edtNewPassword: EditText
    lateinit var edtNewPassword2: EditText
    lateinit var btnReset: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_password, container, false)
        edtEmail1 = view.findViewById(R.id.edtEmail1)
        edtOldPassword = view.findViewById(R.id.edtOldPassword)
        edtNewPassword = view.findViewById(R.id.edtNewPassword)
        edtNewPassword2 = view.findViewById(R.id.edtNewPassword2)
        btnReset = view.findViewById(R.id.btnReset)

        btnReset.setOnClickListener {
                val uname = edtEmail1.text.toString()

                val userCredsDao = AppDatabase.getInstance(requireContext()).userCredsDao()
                CoroutineScope(Dispatchers.IO).launch {
                    val userCredentials = userCredsDao.getCredByUsername(uname)

                    if(userCredentials != null){
                        activity?.runOnUiThread{
                            if(validateInput()){
                                if(BCrypt.verifyer().verify(edtOldPassword.text.toString().trim().toCharArray(), userCredentials.password).verified){
                                    val newPass1 = BCrypt.withDefaults().hashToString(12,edtNewPassword.text.toString().trim().toCharArray())

                                    CoroutineScope(Dispatchers.IO).launch {
                                        userCredsDao.changePasswordByEmail(uname, newPass1)
                                    }

                                    activity?.runOnUiThread {
                                        Toast.makeText(requireContext(),"Password Changed",Toast.LENGTH_SHORT).show()
                                    }
                                    val frag = requireActivity().supportFragmentManager.beginTransaction()
                                    frag.replace(R.id.authFrame,LoginFragment())
                                    frag.commit()
                                }else{
                                    activity?.runOnUiThread{
                                        Toast.makeText(requireContext(),"Invalid Old Password", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }else{
                        activity?.runOnUiThread{
                            Toast.makeText(requireContext(),"User not found", Toast.LENGTH_SHORT).show()
                        }
                    }

            }

        }

        return view
    }

    fun validateInput(): Boolean{

        if(edtEmail1.text.toString().trim() == ""){
            edtEmail1.error = "Enter Email"
            return false
        }

        if(edtOldPassword.text.toString().trim() == ""){
            edtOldPassword.error = "Enter old password"
            return false
        }

        if(edtNewPassword.text.toString().trim() == ""){
            edtNewPassword.error = "Enter new password"
            return false
        }

        if(edtNewPassword.text.toString().trim().length < 8){
            edtNewPassword.error = "Password length must be greater than 8"
            return false
        }

        if(edtNewPassword2.text.toString().trim() == ""){
            edtNewPassword2.error = "Re-Enter new password"
            return false
        }

        if(edtNewPassword.text.toString().trim() != edtNewPassword2.text.toString().trim()){
            edtNewPassword2.error = "Passwords don't match"
            edtNewPassword.error = "Passwords don't match"
            return false
        }
        return true
    }
}