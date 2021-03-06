package com.uni.pis.Events

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.UsersAdapter.InviteeListAdapter
import com.uni.pis.Adapter.UsersAdapter.InviteeListAdapter.Companion.total
import com.uni.pis.Adapter.UsersAdapter.InviteeListAdapter.Companion.totalAccepted
import com.uni.pis.Adapter.UsersAdapter.InviteeListAdapter.Companion.totalmaby
import com.uni.pis.Adapter.UsersAdapter.InviteeListAdapter.Companion.totalrejeted
import com.uni.pis.BackgroundWorker
import com.uni.pis.R
import com.uni.pis.Data.UserData.InviteeListData
import kotlinx.android.synthetic.main.activity_invitee__list.*

class Invitee_List : AppCompatActivity(),BackgroundWorker.MyCallback, InviteeListAdapter.FragCallbacks {
    val InviteeArrayList=ArrayList<InviteeListData>()
    val InviteeArrayListAdapter= InviteeListAdapter(InviteeArrayList, this)
    lateinit  var EventId:String
    enum class userDataOrder(val index: Int) {
        UserID(0),
        firstName(1),
        lastName(2),
        Image(3),
        attendace(4),
        permission(5),
        inviteenumber(6)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invitee__list)

        if(intent.hasExtra("eventID")) {
            EventId = intent.extras!!.get("eventID").toString()
            var data = BackgroundWorker(this)
            data.execute("eventinvittee", EventId)
        }
        total=0
        totalAccepted=0
        totalrejeted=0
        totalmaby=0

    }

    override fun onResult(result: String?) {

        var data=result!!.split("*")
        val builder = AlertDialog.Builder(this)

        for(i in data) {

            var friend=i.split("!&")
            if (friend.size>1){

                var UserID = friend[userDataOrder.UserID.index].substringAfter("=")
                var firstname = friend[userDataOrder.firstName.index].substringAfter("=")
                var lastname = friend[userDataOrder.lastName.index].substringAfter("=")
                var image = friend[userDataOrder.Image.index].substringAfter("=").replace("\\","").trim()
                var attendence = friend[userDataOrder.attendace.index].substringAfter("=")
                var permission = friend[userDataOrder.permission.index].substringAfter("=")
                var inviteenumber = friend[userDataOrder.inviteenumber.index].substringAfter("=")
                InviteeArrayList.add(
                    InviteeListData(
                        UserID,
                        "$firstname  $lastname",
                        image,
                        attendence,
                        permission,
                        inviteenumber
                    )
                )
            }

        }
        RV_InviteeList.layoutManager= LinearLayoutManager(this)
        RV_InviteeList.adapter=InviteeArrayListAdapter
    }

    override fun sendResult(total: Int,totalaccepted: Int,totalrejected: Int,totalmaby: Int) {
        tv_TotalNumber.text=total.toString()
        tv_TotalAccepted.text=totalaccepted.toString()
        tv_TotalRejected.text=totalrejected.toString()
        tv_TotalNotSure.text=totalmaby.toString()
    }
}
