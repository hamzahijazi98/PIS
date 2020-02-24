package com.uni.pis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uni.pis.test.MyCallback
import kotlinx.android.synthetic.main.activity_profile_page.*
import kotlinx.android.synthetic.main.activity_profile_page.tv_city
import kotlinx.android.synthetic.main.activity_profile_page.tv_gender as tv_gender1


class profilePage : AppCompatActivity(), MyCallback {
    var firstName: String? = ""
    var lastName: String? = ""
    var email: String? = ""
    var birth_day: String? = ""
    var birth_month: String? = ""
    var birth_year: String? = ""
    var phone: String? = ""
    var gender: String? = ""
    var city: String? = ""
    override fun onResult(result: String) {
        firstName = result.substringAfter("Fname").substringBefore("Lname")
        lastName = result.substringAfter("Lname").substringBefore("phone")
        phone = result.substringAfter("phone").substringBefore("gender")
        city = result.substringAfter("city")
        birth_day = result.substringAfter("Birthday").substringBefore("-")
        birth_month = result.substringAfter("-").substringBefore("-")
        birth_year = result.substringAfter("-").substringBefore("city")
        email = result.substringAfter("email").substringBefore("Birthday")
        gender = result.substringAfter("gender","hello").substringBefore("email","hello")
        tv_fullName.text=firstName+lastName
        tv_city.text=city
        tv_email.text=email
        tv_gender1.text=gender
        tv_phone.text=phone
        tv_birthday.text= "$birth_day-$birth_month-$birth_year"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        var data = com.uni.pis.test(this)
        data.execute("login","123456")


    }




}
