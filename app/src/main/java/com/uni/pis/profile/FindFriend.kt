package com.uni.pis.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.FindFriendAdapter
import com.uni.pis.Adapter.FriendViewAdapter
import com.uni.pis.BackgroundWorker
import com.uni.pis.Events.EvenstList
import com.uni.pis.Events.Sinviter
import com.uni.pis.Events.UserID
import com.uni.pis.Events.mFirebaseAuth
import com.uni.pis.R
import com.uni.pis.data.friendData
import com.uni.pis.model.EventsListeItem
import com.uni.pis.model.FriendsItem
import kotlinx.android.synthetic.main.activity_create__invitation.*
import kotlinx.android.synthetic.main.activity_find_friend.*
import java.util.*
import kotlin.collections.ArrayList

class FindFriend : AppCompatActivity(),BackgroundWorker.MyCallback {

    val friendarraylist=ArrayList<friendData>()
    val friendarraylistadapter=FindFriendAdapter(friendarraylist,this)
    var userID= mFirebaseAuth.currentUser?.uid!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_friend)

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                friendarraylist.clear()
                rv_findfriend.adapter=friendarraylistadapter
                if (s.toString().isNotEmpty()) {
                    var data = BackgroundWorker(this@FindFriend)
                    data.execute("findfriend", s.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


                }
        })


    }



    override fun onResult(result: String?) {
        var data = result!!.split("*")

        for (i in data) {

            var friend = i.split("&")
            if (friend.size > 1) {

                var firstname=friend[BackgroundWorker.userDataOrder.firstName.index].substringAfter("=")
                var last_name=friend[BackgroundWorker.userDataOrder.lastName.index].substringAfter("=")
                var phoneNumber=friend[BackgroundWorker.userDataOrder.phoneNumber.index].substringAfter("=")
                var gender=friend[BackgroundWorker.userDataOrder.gender.index].substringAfter("=")
                var email=friend[BackgroundWorker.userDataOrder.email.index].substringAfter("=")
                var birthdate=friend[BackgroundWorker.userDataOrder.birthday.index].substringAfter("=")
                var city=friend[BackgroundWorker.userDataOrder.city.index].substringAfter("=")
                var image=friend[BackgroundWorker.userDataOrder.image.index].substringAfter("=").replace("\\","").trim()
                var friendID=friend[BackgroundWorker.userDataOrder.UserID.index].substringAfter("=").trim()
                if(friendID != userID){
                    friendarraylist.add(friendData(firstname, last_name, email, phoneNumber, gender, city, birthdate, image, friendID))
                }
                }



        }

        rv_findfriend.layoutManager=LinearLayoutManager(this)
        rv_findfriend.adapter=friendarraylistadapter

    }

}
