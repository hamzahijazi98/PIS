package com.uni.pis

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.NotificationListAdapter
import com.uni.pis.data.acceptFriendData
import kotlinx.android.synthetic.main.activity_notification.*
import java.lang.Exception


class Notification : AppCompatActivity() {
    val NotificationArrayList=ArrayList<acceptFriendData>()
    val NotificationAdapter=NotificationListAdapter(NotificationArrayList,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        try {



            val userId = intent.extras!!.get("UserID") as String
            val frinedId = intent.extras!!.get("FriendID")as String
            val image = intent.extras!!.get("Image")as String
            val name = intent.extras!!.get("name")as String
            NotificationArrayList.add(acceptFriendData(name, image, userId, frinedId))
        }
        catch (e:Exception)
        {
            NotificationArrayList.add(acceptFriendData("you dont have friend"," ", " "," "))
        }


        rv_notification.layoutManager = LinearLayoutManager(this)
        rv_notification.adapter = NotificationAdapter
    }

}
