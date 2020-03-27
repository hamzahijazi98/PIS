package com.uni.pis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.MyEventListAdapter
import com.uni.pis.Adapter.type
import com.uni.pis.model.EventsListeItem
import com.uni.pis.model.FriendsItem
import kotlinx.android.synthetic.main.activity_evenst_list.*
import kotlinx.android.synthetic.main.activity_friends.*


class EvenstList : AppCompatActivity(),BackgroundWorker.MyCallback {
    val arrayListMyEve=ArrayList<EventsListeItem>()
    val arrayListMyInv=ArrayList<EventsListeItem>()
    val event2Adapter=MyEventListAdapter(arrayListMyInv,this)
    val eventAdapter=MyEventListAdapter(arrayListMyEve,this)
    enum class eventDataOrder(val index: Int) {
        EventID(0),
        Name(1),
        Description(2),
        Image(3)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evenst_list)
        var userID=mFirebaseAuth.currentUser?.uid!!
        var data = BackgroundWorker(this)
        data.execute("myevents",userID )
        data = BackgroundWorker(this)
        data.execute("invitedtoevent",userID )


//        arrayListMyInv.add(EventsListeItem("Wedding","basil mmmaaa2222 ",R.drawable.ic_notifications_black_24dp))
//        arrayListMyInv.add(EventsListeItem("Wedding","basil mmmaaa2222 ",R.drawable.ic_notifications_black_24dp))
//        arrayListMyInv.add(EventsListeItem("Wedding","basil mmmaaa2222 ",R.drawable.ic_notifications_black_24dp))






    }

    override fun onResult(result: String?) {
        var type = result!!.split("^")
        if (type[1] == "myevents") {
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
                    arrayListMyEve.add(EventsListeItem(EventID, name, description, Image))
                }

            }


            rv_Myeven.layoutManager = LinearLayoutManager(this)
            rv_Myeven.adapter = eventAdapter

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
                    arrayListMyInv.add(EventsListeItem(EventID, name, description, Image))
                }

            }


            rv_invevents.layoutManager=LinearLayoutManager(this)
            rv_invevents.adapter=event2Adapter
        }
    }
}
