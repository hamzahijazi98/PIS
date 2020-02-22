package com.uni.pis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.uni.pis.ui.login.LoginActivity
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUp : AppCompatActivity() {
    private val phone_domain= arrayOf("078","077","079")

    //var mFirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        /*val username = findViewById<EditText>(R.id.Email)
        val password = findViewById<EditText>(R.id.password)
        val signup = findViewById<Button>(R.id.signup)
        val loading = findViewById<ProgressBar>(R.id.loading)*/

        //yousef
        val password = findViewById<EditText>(R.id.password)
        val comfim_password=findViewById<EditText>(R.id.password1)
        val fname=findViewById<EditText>(R.id.et_firstname)
        val lname=findViewById<EditText>(R.id.et_lastname)
        var phoneno:String
        var gender:String


        val myAdapterView=ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,phone_domain)
        spinner_phone.adapter=myAdapterView






        signup.setOnClickListener {
           // var username1=username.text.toString().trim()
           //var password1=password.text.toString().trim()

             //yousefffffffff
             //first name valid
             if (TextUtils.isEmpty(et_firstname.getText().toString()))
                 et_firstname.error = "Empty field not allowed ... "
             if(et_firstname.text.toString().length>12)
                 et_firstname.error="Name too long ..."

             //last name valid
             if (TextUtils.isEmpty(et_lastname.getText().toString()))
                 et_lastname.error = "Empty field not allowed ... "
             if(et_lastname.text.toString().length>12)
                 et_lastname.error="Name too long ..."


             //email valid
             if(TextUtils.isEmpty(Email.text)||!android.util.Patterns.EMAIL_ADDRESS.matcher(Email.getText()).matches())
                 Email.error="Empty or Invalid Email Address ..."


             //password valid
             if(TextUtils.isEmpty(password.text))
                 password.error="Empty field not allowed ..."

             if(TextUtils.isEmpty(password1.text))
                 password1.error="Empty field not allowed ..."


             if(!password.text.toString().equals(comfim_password.text.toString())) {
                 Toast.makeText(this, "Password do not match ", Toast.LENGTH_LONG).show()
             }
             else{
                 Toast.makeText(this,"Password Match",Toast.LENGTH_LONG).show()
             }

            //phone number valid
             if(TextUtils.isEmpty(phonenumber.text))
                 phonenumber.error="Empty field not allowed ... "

             if(phonenumber.length()!=7)
                 phonenumber.error="Invalid phone number ..."



             spinner_phone.onItemSelectedListener=object :AdapterView.OnItemSelectedListener
             {
                 override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                     phoneno=phone_domain[position]+phonenumber.text.toString()
                 }

                 override fun onNothingSelected(parent: AdapterView<*>?) {

                 }
             }



            //gender check
            //gender check  
            if(RB_male.isChecked)
                gender="male"
            else
                gender="female"














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
}
