package com.uni.pis.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.UsersAdapter.FindFriendAdapter
import com.uni.pis.BackgroundWorker
import com.uni.pis.Events.mFirebaseAuth
import com.uni.pis.R
import com.uni.pis.Data.UserData.friendData
import kotlinx.android.synthetic.main.activity_find_friend.*


class FindFriend : AppCompatActivity(),BackgroundWorker.MyCallback {

    val FriendArraylist=ArrayList<friendData>()
    val FreindArrayListAdapter= FindFriendAdapter(FriendArraylist, this)
    var userID= mFirebaseAuth.currentUser?.uid!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_friend)

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                FriendArraylist.clear()
                rv_findfriend.adapter=FreindArrayListAdapter
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

            var friend = i.split("&&")
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
                    FriendArraylist.add(
                        friendData(
                            firstname,
                            last_name,
                            email,
                            phoneNumber,
                            gender,
                            city,
                            birthdate,
                            image,
                            friendID
                        )
                    )
                }
                }



        }

        rv_findfriend.layoutManager=LinearLayoutManager(this)
        rv_findfriend.adapter=FreindArrayListAdapter

    }

}
