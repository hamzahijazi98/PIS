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
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUp : AppCompatActivity() {
    var mFirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val username = findViewById<EditText>(R.id.Email)
        val password = findViewById<EditText>(R.id.password)
        val signup = findViewById<Button>(R.id.signup)
        val loading = findViewById<ProgressBar>(R.id.loading)

        //yousef
        val confpass=findViewById<EditText>(R.id.password1)
        val fname=findViewById<EditText>(R.id.et_firstname)
        val lname=findViewById<EditText>(R.id.et_lastname)

        if(TextUtils.isEmpty(et_firstname.text.toString()))
            fname.error="No empty field is allowed ... "
        if(TextUtils.isEmpty(et_lastname.text.toString()))
            lname.error="No empty field is allowed ... "

        val email=findViewById<EditText>(R.id.Email)
        if(TextUtils.isEmpty(Email.text.toString().trim())||!android.util.Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches())
            Email.error="Empty or Invalid email address"

        if(password.length()<5)
            password.error="Your password is too short "
        if(password!=confpass)
            password.error="Password Mismatch "





        signup.setOnClickListener {
            var username1=username.text.toString().trim()
            var password1=password.text.toString().trim()





            mFirebaseAuth.createUserWithEmailAndPassword(
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
            }
        }
    }
}
