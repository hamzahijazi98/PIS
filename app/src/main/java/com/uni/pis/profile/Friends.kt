package com.uni.pis.profile

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.FriendViewAdapter
import com.uni.pis.BackgroundWorker
import com.uni.pis.R
import com.uni.pis.Events.mFirebaseAuth
import com.uni.pis.data.eventData
import com.uni.pis.data.model.eventDataInvite
import com.uni.pis.model.FriendsItem
import kotlinx.android.synthetic.main.activity_create__invitation.view.*
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.activity_public_page_profile.*
import kotlinx.android.synthetic.main.activity_public_page_profile.btn_invite
import kotlinx.android.synthetic.main.cardview_friend_list.*
import kotlinx.android.synthetic.main.fragment_profile_page_personal.view.*
import kotlinx.android.synthetic.main.invitationdialog.view.*

class Friends : AppCompatActivity(), BackgroundWorker.MyCallback {
    val arraylist=ArrayList<FriendsItem>()
    val friendadap=FriendViewAdapter(arraylist,this)
    enum class userDataOrder(val index: Int) {
        UserID(0),
        firstName(1),
        lastName(2),
        Image(3)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        var userID= mFirebaseAuth.currentUser?.uid!!
        var data = BackgroundWorker(this)
        data.execute("myfriends",userID )

        if(intent.hasExtra("EventId")) {
        val eventID=intent.extras!!.get("EventId")
        val invitee_Number=intent.extras!!.get("InviteeNumber")
            val channelUrl=intent.extras!!.get("channelUrl")



            eventDataInvite.EventID = eventID.toString()
            eventDataInvite.InviteeNumer=invitee_Number.toString()
            eventDataInvite.channelUrl=channelUrl.toString()
        }





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
            arraylist.add(FriendsItem(UserID,"$firstname  $lastname",image))
            }

        }

        rv_friend.layoutManager= LinearLayoutManager(this)
        rv_friend.adapter=friendadap
    }
}
