package com.uni.pis.Events

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.MyEventListAdapter
import com.uni.pis.BackgroundWorker
import com.uni.pis.R
import com.uni.pis.model.EventsListeItem
import kotlinx.android.synthetic.main.activity_evenst_list.*


class EvenstList : AppCompatActivity(), BackgroundWorker.MyCallback {
    val arrayListMyEvent=ArrayList<EventsListeItem>()
    val arrayListMyInvited=ArrayList<EventsListeItem>()
    val AdapterInvitedEvent=MyEventListAdapter(arrayListMyInvited,this)
    val AdapterMyEvent=MyEventListAdapter(arrayListMyEvent,this)
    enum class eventDataOrder(val index: Int) {
        EventID(0),
        Name(1),
        Description(2),
        Image(3)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evenst_list)
        var userID= mFirebaseAuth.currentUser?.uid!!
        var data = BackgroundWorker(this)
        data.execute("myevents",userID )
        data = BackgroundWorker(this)
        data.execute("invitedtoevent",userID )

    }

    override fun onResult(result: String?) {
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
                    arrayListMyEvent.add(EventsListeItem(EventID, name, description, Image))
                }

            }


            rv_Myeven.layoutManager = LinearLayoutManager(this)
            rv_Myeven.adapter = AdapterMyEvent

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
                    arrayListMyInvited.add(EventsListeItem(EventID, name, description, Image))
                }

            }


            rv_invevents.layoutManager=LinearLayoutManager(this)
            rv_invevents.adapter=AdapterInvitedEvent
        }
    }
}
