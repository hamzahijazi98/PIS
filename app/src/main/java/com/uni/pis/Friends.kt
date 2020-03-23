package com.uni.pis

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.EventsAdapter
import com.uni.pis.Adapter.FriendViewAdapter
import com.uni.pis.model.FriendsItem
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.fragment_events_.*

class Friends : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)


        val arraylist=ArrayList<FriendsItem>()
        arraylist.add(FriendsItem("yousef",R.drawable.ic_image_black_24dp))
        arraylist.add(FriendsItem("yousef",R.drawable.ic_image_black_24dp))
        arraylist.add(FriendsItem("yousef",R.drawable.ic_image_black_24dp))
        arraylist.add(FriendsItem("yousef",R.drawable.ic_image_black_24dp))
        arraylist.add(FriendsItem("yousef",R.drawable.ic_image_black_24dp))

        val friendadap=FriendViewAdapter(arraylist,this)
        rv_friend.layoutManager= LinearLayoutManager(this)
        rv_friend.adapter=friendadap


















    }
}
