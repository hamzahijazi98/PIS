package com.uni.pis

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.uni.pis.data.userData

import kotlinx.android.synthetic.main.activity_profile_page.*
import kotlinx.android.synthetic.main.activity_profile_page.tv_city
import java.lang.NullPointerException
import kotlinx.android.synthetic.main.activity_profile_page.tv_gender as tv_gender1


class profilePage : AppCompatActivity(), BackgroundWorker.MyCallback {
    var firstName: String? = ""
    var lastName: String? = ""
    var email: String? = ""
    var birth_day: String? = ""
    var birth_month: String? = ""
    var birth_year: String? = ""
    var phone: String? = ""
    var gender: String? = ""
    var city: String? = ""
    var image: String? = ""
    override fun onResult(result: String?) {

        tv_fullName.text=userData.first_name+userData.last_name
        tv_city.text=userData.city
        tv_email.text=userData.email
        tv_gender1.text=userData.gender
        tv_phone.text=userData.phoneNumber
        tv_birthday.text= userData.birthdate


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        try {
            var mFirebaseAuth = FirebaseAuth.getInstance()

            var data = BackgroundWorker(this)
            data.execute("login", mFirebaseAuth.currentUser?.uid!!)



        }
        catch (e:NullPointerException)
        {
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }


    }




}
