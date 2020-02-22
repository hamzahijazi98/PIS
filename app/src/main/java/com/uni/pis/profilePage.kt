package com.uni.pis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class profilePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        var data = com.uni.pis.test(this)
        data.execute("login","123455")
    }
}
