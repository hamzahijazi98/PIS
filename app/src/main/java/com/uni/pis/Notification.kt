package com.uni.pis

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


            val intent = intent
            val userId = intent.getStringExtra("UserID")
            val frinedId = intent.getStringExtra("FriendID")
            val image = intent.getStringExtra("Image")
            val name = intent.getStringExtra("name")
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
