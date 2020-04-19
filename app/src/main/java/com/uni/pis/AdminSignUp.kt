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









    et_email.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            et_email.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.text).matches())
                    et_email.error = "Invalid Email Address ..."
            }
            if (s!!.isEmpty()) {
                et_email.error = "Empty Field Not Allowed ..."
                email = ""
            } else
                email = et_email.text.toString()
        }
    })
    et_password.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            et_password.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
                if (s!!.length < 6)
                    et_password.error = "Minimum Length Password is 6"
            }
            if (s!!.isEmpty())
                et_password.error = "Empty Field Not Allowed ..."
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_sign_up)
    }
}
