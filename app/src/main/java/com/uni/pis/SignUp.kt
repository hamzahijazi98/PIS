package com.uni.pis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.uni.pis.ui.login.LoginActivity

class SignUp : AppCompatActivity() {
    var mFirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val username = findViewById<EditText>(R.id.Email)
        val password = findViewById<EditText>(R.id.password)
        val signup = findViewById<Button>(R.id.signup)
        val loading = findViewById<ProgressBar>(R.id.loading)

        signup.setOnClickListener {
            var username1=username.text.toString().trim()
            var password1=password.text.toString().trim()
            mFirebaseAuth.createUserWithEmailAndPassword(
                username1,password1
            ).addOnCompleteListener {
                if (!it.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Faild to login Username or Password Invalid $username1,$password1",
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
