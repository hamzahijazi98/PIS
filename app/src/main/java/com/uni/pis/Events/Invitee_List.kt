package com.uni.pis.Events

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.FriendViewAdapter
import com.uni.pis.Adapter.InviteeListAdapter
import com.uni.pis.BackgroundWorker
import com.uni.pis.R
import com.uni.pis.data.InviteeListData
import com.uni.pis.data.model.eventDataInvite
import com.uni.pis.model.FriendsItem
import com.uni.pis.profile.Friends
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.activity_invitee__list.*

class Invitee_List : AppCompatActivity(),BackgroundWorker.MyCallback {
    val InviteeArrayList=ArrayList<InviteeListData>()
    val InviteeArrayListAdapter= InviteeListAdapter(InviteeArrayList,this)
    enum class userDataOrder(val index: Int) {
        UserID(0),
        firstName(1),
        lastName(2),
        Image(3),
        attendace(4),
        permission(5)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invitee__list)


        var userID= mFirebaseAuth.currentUser?.uid!!
        var data = BackgroundWorker(this)
        data.execute("myfriends",userID )


    }

    override fun onResult(result: String?) {

        var data=result!!.split("*")
        val builder = AlertDialog.Builder(this)

        for(i in data) {

            var friend=i.split("&")
            if (friend.size>1){

                var UserID = friend[userDataOrder.UserID.index].substringAfter("=")
                var firstname = friend[userDataOrder.firstName.index].substringAfter("=")
                var lastname = friend[userDataOrder.lastName.index].substringAfter("=")
                var image = friend[userDataOrder.Image.index].substringAfter("=").replace("\\","").trim()
                var attendence = friend[userDataOrder.attendace.index].substringAfter("=")
                var permission = friend[userDataOrder.permission.index].substringAfter("=")
                InviteeArrayList.add(InviteeListData(UserID,"$firstname  $lastname",image,attendence,permission))
            }

        }
        RV_InviteeList.layoutManager= LinearLayoutManager(this)
        RV_InviteeList.adapter=InviteeArrayListAdapter
    }
}
