package com.uni.pis
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.*
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*

class SignUp : AppCompatActivity() {
    private val phone_domain = arrayOf("078 / 079 / 077","078", "077", "079")
    private val cities = arrayOf("Choose your city","Amman", "Az Zarqa", "Irbid", "Al Karak",
        "Jerash", "Ajloun", "Ma'an", "Tafilah", "Madaba", "Aqaba", "Balqa", "Mafraq")
    //var mFirebaseAuth = FirebaseAuth.getInstance()


    lateinit var first_name: String
    lateinit var lastname: String
    lateinit var email: String
    lateinit var password: String
    lateinit var phonenumber: String
    lateinit var gender: String
    lateinit var date_of_birth: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        /*Hamzeh
        val username = findViewById<EditText>(R.id.Email)
        val password = findViewById<EditText>(R.id.password)
        val signup = findViewById<Button>(R.id.signup)
        val loading = findViewById<ProgressBar>(R.id.loading)*/




        et_firstname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_firstname.text.isEmpty())
                    et_firstname.error = "Empty field not allowed ... "
                if (et_firstname.text.toString().trim().length > 12)
                    et_firstname.error = "name too long"
                else
                    first_name = et_firstname.text.toString()
            }

        })

        et_lastname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_lastname.text.isEmpty()) {
                    et_lastname.error = "Empty field not allowed ... "
                    btn_signup.isEnabled = false
                }
                if (et_lastname.text.toString().trim().length > 12) {
                    et_lastname.error = "name too long"
                    btn_signup.isEnabled = false
                } else {
                    lastname = et_lastname.text.toString()
                }

            }


        })

        et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (et_email.text.isEmpty())
                    et_email.error = "Empty field not allowed ..."

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.text).matches())
                    et_email.error = "Invalid Email Address ..."
                else
                    email = et_email.text.toString()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        et_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_birthdate.isEnabled = check_password()
            }

        })

        et_repassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_birthdate.isEnabled = repassword_check()

            }
        })


//phone number code
        val phone_adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, phone_domain)

        spinner_phone.adapter = phone_adapter
        spinner_phone.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0)
                    btn_birthdate.isEnabled = false
                else
                    phonenumber = phone_domain[position] + et_phonenumber.text.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                btn_signup.isEnabled = false

            }
        }

        et_phonenumber.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_phonenumber.text.isEmpty()) {
                    et_phonenumber.error = "Empty field not allowed ... "
                    btn_signup.isEnabled = false
                }
                if (et_phonenumber.length() != 7) {
                    et_phonenumber.error = "Invalid phone number ..."
                    btn_signup.isEnabled = false
                }

            }

        })

        RG_gender.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.RB_male)
                gender = "male"
            if (checkedId == R.id.RB_female)
                gender = "female"
        }



//Birthdate
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        //button birth date birthdate
        btn_birthdate.setOnClickListener {
            val date_of_birth = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    tv_date.text = (" $dayOfMonth--$month--$year")
                }, year, month, day
            )
            date_of_birth.show()
        }




// city code
        var city = ""
        val city_adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities)
        spinner_city.adapter = city_adapter
        spinner_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position==0){
                    btn_signup.isEnabled=false
                }
                else {
                    city = cities[position]
                    if (et_firstname.text.isNotEmpty()&&et_lastname.text.isNotEmpty()&&et_password.text.isNotEmpty()&&et_repassword.text.isNotEmpty()&&et_phonenumber.text.isNotEmpty())
                        btn_signup.isEnabled=true
                    else
                        btn_signup.isEnabled=false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }



        btn_signup.setOnClickListener {


            Toast.makeText(
                this,
                "your first name $first_name and your last name is $lastname",
                Toast.LENGTH_LONG
            ).show()


            // var username1=username.text.toString().trim()
            //var password1=password.text.toString().trim()

            /*   mFirebaseAuth.createUserWithEmailAndPassword(
                 username1,password1
            ).addOnCompleteListener {
                if (!it.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Failed to login Username or Password Invalid $username1,$password1",
                        Toast.LENGTH_LONG
                    ).show()
                } else {

                    Toast.makeText(
                        this,
                        "$username1,$password1",
                        Toast.LENGTH_LONG
                    ).show()

                    intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }*/

        }

    }
    fun repassword_check():Boolean{
        if(et_repassword.text.isEmpty())
        {
            et_repassword.error="empty"
            return false
        }
        if(et_repassword.text.trim().length<5)
        {
            et_repassword.error="short"
            return false
        }
        else if(et_repassword.text.toString().equals(et_password.text.toString()))
        {
            password=et_password.text.toString()
        }
        return true



    }
    fun check_password(): Boolean {

        if (et_password.text.isEmpty()) {
            et_password.error = "Empty field not allowed ..."
            return false
        }
        if (et_password.text.trim().length < 5) {
            et_password.error = "Short Password"
            return false
        }
        return true


    }

}