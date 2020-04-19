package com.uni.pis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_sign_up.*

class AdminSignUp : AppCompatActivity() {


    var mFirebaseAuth = FirebaseAuth.getInstance()
    lateinit var mStorageRef: StorageReference
    lateinit var mDatabaseRef: DatabaseReference


    var et_userName: String=""
    var et_adminID: String=""

    var emaill: String=""
    var et_passwordd: String=""
    var et_telephone: String=""







    et_emaill.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val et_emaill
            et_emaill.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(et_emaill.text).matches())
                    et_emaill.error = "Invalid Email Address ..."
            }
            if (s!!.isEmpty()) {
                et_emaill.error = "Empty Field Not Allowed ..."
                et_emaill = ""
            } else
                et_emaill = et_emaill.text.toString()
        }
    })




    et_passwordd.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            et_passwordd.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
                if (s!!.length < 6)
                    et_passwordd.error = "Minimum Length Password is 6"
            }
            if (s!!.isEmpty())
                et_passwordd.error = "Empty Field Not Allowed ..."
        }
    })





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_sign_up)
    }
}
