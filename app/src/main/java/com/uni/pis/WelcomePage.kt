package com.uni.pis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class WelcomePage : AppCompatActivity()    {


    var mFirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.welcome_page)


        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)
        login.isEnabled = true
        login.setOnClickListener {
var username1=username.text.toString().trim()
            var password1=password.text.toString().trim()
            mFirebaseAuth.signInWithEmailAndPassword(
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

                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
