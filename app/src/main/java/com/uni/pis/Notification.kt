package com.uni.pis

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uni.pis.Adapter.NotificationListAdapter
import com.uni.pis.Events.mFirebaseAuth
import com.uni.pis.data.acceptFriendData
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.cardview_notification.*
import java.lang.Exception

enum class userDataOrder(val index: Int) {
    userID(4),
    friendID(5),
    body(2),
    Image(3)
}
class Notification : AppCompatActivity(),BackgroundWorker.MyCallback {
    val NotificationArrayList=ArrayList<acceptFriendData>()
    val NotificationAdapter=NotificationListAdapter(NotificationArrayList,this)
    var userID= mFirebaseAuth.currentUser?.uid!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        try {
            var data = BackgroundWorker(this)
            data.execute("notification","receive",userID )
        }
        catch (e:Exception)
        {
            NotificationArrayList.add(acceptFriendData("you dont have friend"," ", " "," "))
        }
        rv_notification.layoutManager = LinearLayoutManager(this)
        rv_notification.adapter = NotificationAdapter



    }

    override fun onResult(result: String?) {
        var data=result!!.split("*")
        for(i in data) {

            var friend=i.split("&")
            if (friend.size>1){
                var userId = friend[userDataOrder.userID.index].substringAfter("=")
                var frinedId = friend[userDataOrder.friendID.index].substringAfter("=")
                var body = friend[userDataOrder.body.index].substringAfter("=")
                var image = friend[userDataOrder.Image.index].substringAfter("=").replace("\\","").trim()
                NotificationArrayList.add(acceptFriendData(body,image, userId,frinedId))

            }

        }
        rv_notification.layoutManager = LinearLayoutManager(this)
        rv_notification.adapter = NotificationAdapter


    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}
