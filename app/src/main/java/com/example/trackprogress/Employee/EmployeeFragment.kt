    package com.example.trackprogress.Employee

    import android.content.Context.MODE_PRIVATE
    import android.content.SharedPreferences
    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.constraintlayout.widget.ConstraintLayout
    import androidx.lifecycle.lifecycleScope
    import com.example.trackprogress.Admin.Functionalities.ApproveLeaveFragment
    import com.example.trackprogress.Admin.Functionalities.AssignTaskFragment
    import com.example.trackprogress.Database.AppDatabase
    import com.example.trackprogress.Employee.Functionalities.ApplyLeaveFragment
    import com.example.trackprogress.Employee.Functionalities.EmpTaskFragment
    import com.example.trackprogress.Employee.Functionalities.ProfileFragment
    import com.example.trackprogress.Employee.Functionalities.QueryFragment
    import com.example.trackprogress.R
    import kotlinx.coroutines.GlobalScope
    import kotlinx.coroutines.launch
    import java.text.SimpleDateFormat
    import java.util.Calendar

    class EmployeeFragment : Fragment() {

        lateinit var sharedPreferences: SharedPreferences
        lateinit var txtEmp_Name: TextView
        lateinit var txtWish: TextView
        lateinit var cstEmpTask: ConstraintLayout
        lateinit var cstEmpLeave: ConstraintLayout
        lateinit var cstEmpQuery: ConstraintLayout
        lateinit var cstEmpProfile: ConstraintLayout

        private var name = ""
        private var id = 0L
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_employee, container, false)
            txtEmp_Name = view.findViewById(R.id.txtEmp_Name)
            txtWish = view.findViewById(R.id.txtWish)
            cstEmpTask = view.findViewById(R.id.cstEmpTask)
            cstEmpLeave = view.findViewById(R.id.cstEmpLeave)
            cstEmpQuery = view.findViewById(R.id.cstEmpQuery)
            cstEmpProfile = view.findViewById(R.id.cstEmpProfile)

            sharedPreferences = requireContext().getSharedPreferences(getString(R.string.SharedPref), MODE_PRIVATE)
            val uname = sharedPreferences.getString(getString(R.string.Cred_ID),"")!!

            txtWish.setText(greeting())

            lifecycleScope.launch{
                val userDAO = AppDatabase.getInstance(requireContext()).userDao()
                val user = userDAO.getUserByEmail(uname)!!
                name = user.name
                id = user.id!!
                txtEmp_Name.setText("Welcome, $name")

                val bundle = Bundle()
                bundle.putLong("ID",id)

                cstEmpTask.setOnClickListener {
                    val fragment = EmpTaskFragment()
                    fragment.arguments = bundle

                    var myFrag = requireActivity().supportFragmentManager.beginTransaction()
                    myFrag.replace(R.id.employeeFrame,fragment)
                    myFrag.commit()
                }

                cstEmpLeave.setOnClickListener {
                    val fragment = ApplyLeaveFragment()
                    fragment.arguments = bundle

                    var myFrag = requireActivity().supportFragmentManager.beginTransaction()
                    myFrag.replace(R.id.employeeFrame,fragment)
                    myFrag.commit()
                }

                cstEmpQuery.setOnClickListener {
                    val fragment = QueryFragment()
                    fragment.arguments = bundle

                    var myFrag = requireActivity().supportFragmentManager.beginTransaction()
                    myFrag.replace(R.id.employeeFrame,fragment)
                    myFrag.commit()
                }

                cstEmpProfile.setOnClickListener {
                    val fragment = ProfileFragment()
                    fragment.arguments = bundle

                    var myFrag = requireActivity().supportFragmentManager.beginTransaction()
                    myFrag.replace(R.id.employeeFrame,fragment)
                    myFrag.commit()
                }
            }

            return view
        }
        fun greeting(): String{
            val currentTime = Calendar.getInstance().time

            val simpleDateFormat = SimpleDateFormat("HH:mm")
            val time = simpleDateFormat.format(currentTime)

            val hour = time.split(":")[0].toInt()

            var greetingMsg = ""
            if(hour in 0 .. 11){
                greetingMsg = "Good Morning!"
            }else if (hour in 12 .. 17){
                greetingMsg = "Good Afternoon!"
            }else{
                greetingMsg = "Good Evening!"
            }
            return greetingMsg
        }


    }