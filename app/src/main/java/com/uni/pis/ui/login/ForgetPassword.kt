package com.uni.pis.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.uni.pis.R
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        var email=intent.getStringExtra("email")
        var mFirebaseAuth = FirebaseAuth.getInstance()
        if (email!=null)
        et_email.append(email)

        btn_recovery.setOnClickListener {
            if (et_email.text.toString() != ""){
            email = et_email.text.toString()}
            loading.visibility = View.VISIBLE
            mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    Toast.makeText(this,"Re-set Password sent to your email",Toast.LENGTH_LONG).show()
                    loading.visibility = View.INVISIBLE
                }
                else
                {
                    Toast.makeText(this,it.exception?.message!!,Toast.LENGTH_LONG).show()
                    loading.visibility = View.INVISIBLE
                }
            }

        }
    }
}
