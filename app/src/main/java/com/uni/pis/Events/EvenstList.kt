package com.uni.pis.Events

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uni.pis.Adapter.MyEventListAdapter
import com.uni.pis.BackgroundWorker
import com.uni.pis.Events_Frag
import com.uni.pis.HomeFrag
import com.uni.pis.R
import com.uni.pis.homefrags.Invited_Events_Frag
import com.uni.pis.homefrags.MyViewPagerAdapter
import com.uni.pis.homefrags.My_Events_Frag
import com.uni.pis.model.EventsListeItem
import com.uni.pis.profile.ProfilePagePersonalFrag
import kotlinx.android.synthetic.main.activity_evenst_list.*
import kotlinx.android.synthetic.main.activity_evenst_list.tabs
import kotlinx.android.synthetic.main.activity_evenst_list.view_pager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_my__events.*


class EvenstList : AppCompatActivity(), BackgroundWorker.MyCallback {
    enum class eventDataOrder(val index: Int) {
        EventID(0),
        Name(1),
        Description(2),
        Image(3)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evenst_list)
        val viewpage_apdapter = MyViewPagerAdapter(supportFragmentManager)
        viewpage_apdapter.addfragment(My_Events_Frag(), "My Events")
        viewpage_apdapter.addfragment(Invited_Events_Frag(), "Event's Invited To")
        view_pager.adapter = viewpage_apdapter
        tabs.setupWithViewPager(view_pager)
    }
    override fun onResult(result: String?) {
        val myEventFrag:My_Events_Frag =My_Events_Frag()
        val InvitedEventFrag:Invited_Events_Frag= Invited_Events_Frag()
        var type = result!!.split("^")
        if (type[1] == "myevents") {
            var data = type[2].split("*")
            for (i in data) {

                var friend = i.split("&")
                if (friend.size > 1) {

                    var EventID = friend[eventDataOrder.EventID.index].substringAfter("=")
                    var name = friend[eventDataOrder.Name.index].substringAfter("=")
                    var description = friend[eventDataOrder.Description.index].substringAfter("=")
                    var Image =
                        friend[eventDataOrder.Image.index].substringAfter("=").replace("\\", "")
                            .trim()

                    myEventFrag.arrayListMyEvent.add(EventsListeItem(EventID, name, description, Image))
                }
            }
            var RV_myevent=findViewById<RecyclerView>(R.id.rv_Myeven)
            RV_myevent.layoutManager = LinearLayoutManager(this)
            RV_myevent.adapter = myEventFrag.AdapterMyEvent

        }
        if (type[1] == "invitedto")
        {
            var data = type[2].split("*")
            val builder = AlertDialog.Builder(this)

            for (i in data) {

                var friend = i.split("&")
                if (friend.size > 1) {

                    var EventID = friend[eventDataOrder.EventID.index].substringAfter("=")
                    var name = friend[eventDataOrder.Name.index].substringAfter("=")
                    var description = friend[eventDataOrder.Description.index].substringAfter("=")
                    var Image =
                        friend[eventDataOrder.Image.index].substringAfter("=").replace("\\", "")
                            .trim()
                    InvitedEventFrag.arrayListMyInvited.add(EventsListeItem(EventID, name, description, Image))
                }
            }
            var RV_INVITEDEVENT=findViewById<RecyclerView>(R.id.rv_invevents)
            RV_INVITEDEVENT.layoutManager = LinearLayoutManager(this)
            RV_INVITEDEVENT.adapter = InvitedEventFrag.AdapterInvitedEvent


        }
    }
}