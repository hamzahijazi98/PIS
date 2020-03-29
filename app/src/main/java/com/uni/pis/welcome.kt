package com.uni.pis

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.uni.pis.homefrags.MainActivity
import com.uni.pis.ui.login.LoginActivity

class welcome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        var sp= getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        var email= sp.getString("name",null)
        var password =sp.getString("password", null)
        var mFirebaseAuth = FirebaseAuth.getInstance()
        var intent:Intent
        if (email!= null && password!= null && mFirebaseAuth.currentUser!= null){

                intent=Intent(this,MainActivity::class.java)
        }
        else
        {
                intent=Intent(this,LoginActivity::class.java)

        }

        val handler = Handler()
        handler.postDelayed(
            Runnable {startActivity(intent)
            finish()
            },
            1000
        )



    }
}
