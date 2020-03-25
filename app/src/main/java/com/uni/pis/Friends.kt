package com.uni.pis

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.EventsAdapter
import com.uni.pis.Adapter.FriendViewAdapter
import com.uni.pis.model.FriendsItem
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.fragment_events_.*

class Friends : AppCompatActivity(),BackgroundWorker.MyCallback {
    val arraylist=ArrayList<FriendsItem>()
    enum class userDataOrder(val index: Int) {
        UserID(0),
        firstName(1),
        lastName(2),
        Image(3)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        var userID=mFirebaseAuth.currentUser?.uid!!
        var data = BackgroundWorker(this)
        data.execute("myfriends",userID )
        val friendadap=FriendViewAdapter(arraylist,this)
        rv_friend.layoutManager= LinearLayoutManager(this)
        rv_friend.adapter=friendadap

    }

    override fun onResult(result: String?) {
        var data=result!!.split("*")
        val builder = AlertDialog.Builder(this)

       for(i in data) {

            var friend=data[0].split("&")
            if (friend.size>1){
            var UserID = friend[userDataOrder.UserID.index].substringAfter("=")
            var firstname = friend[userDataOrder.firstName.index].substringAfter("=")
            var lastname = friend[userDataOrder.lastName.index].substringAfter("=")
            var image = friend[userDataOrder.Image.index].substringAfter("=").replace("\\","").trim()
            arraylist.add(FriendsItem(UserID,"$firstname  $lastname",image))

            }

        }
    }
}
