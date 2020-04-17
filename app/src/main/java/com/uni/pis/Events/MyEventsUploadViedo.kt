package com.uni.pis.Events

import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.Adapter.EventsAdapter.MyEventsListVideoAdapter
import com.uni.pis.BackgroundWorker
import com.uni.pis.Data.EventData.eventData
import com.uni.pis.R
import kotlinx.android.synthetic.main.activity_my_events_upload_viedo.*

class MyEventsUploadViedo : AppCompatActivity(),BackgroundWorker.MyCallback {
    lateinit var  viedoURI:Uri
    lateinit var MyEventArrayList:ArrayList<eventData>
    lateinit var MyEventArrayListAdapter:MyEventsListVideoAdapter

    enum class eventDataOrder(val index: Int) {
        EventID(0),
        Invitee_No(1),
        StartTime(2),
        EndTime(3),
        EventType(4),
        Date(5),
        Description(6),
        PlaceId(7),
        Image(8),
        FirstInviterName(9),
        SecondInviterName(10),
        InviterId(11),
        ChannelUrl(12),
        Video(13)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_events_upload_viedo)
        mStorageRef = FirebaseStorage.getInstance().getReference("eventVideo")
        if(intent.hasExtra("video"))
            viedoURI=intent.extras!!.get("video").toString().toUri()
        MyEventArrayList = ArrayList<eventData>()
        MyEventArrayListAdapter= MyEventsListVideoAdapter(MyEventArrayList,viedoURI)
        try {
            var userID= mFirebaseAuth.currentUser?.uid!!
            var data = BackgroundWorker(this)
            data.execute("myevents",userID )
        }
        catch (e:Exception)
        {
            Toast.makeText(this,e.message, Toast.LENGTH_LONG).show()
        }



    }

    override fun onResult(result: String?) {
        var type = result!!.split("^")
        if (type.size > 2) {
            if (type[1] == "myevents") {
                var data = type[2].split("*")
                for (i in data) {

                    var friend = i.split("!&")
                    if (friend.size > 1) {
                        var EventID = friend[eventDataOrder.EventID.index].substringAfter("=")
                        var Invitee_No = friend[eventDataOrder.Invitee_No.index].substringAfter("=")
                        var StartTime = friend[eventDataOrder.StartTime.index].substringAfter("=")
                        var EndTime = friend[eventDataOrder.EndTime.index].substringAfter("=")
                        var name = friend[eventDataOrder.EventType.index].substringAfter("=")
                        var Date = friend[eventDataOrder.Date.index].substringAfter("=")
                        var description = friend[eventDataOrder.Description.index].substringAfter("=")
                        var PlaceId = friend[eventDataOrder.PlaceId.index].substringAfter("=")
                        var Image = friend[eventDataOrder.Image.index].substringAfter("=").replace("\\", "")
                        var FirstInviterName =
                            friend[eventDataOrder.FirstInviterName.index].substringAfter("=")
                        var SecondInviterName =
                            friend[eventDataOrder.SecondInviterName.index].substringAfter("=")
                        var InviterId = friend[eventDataOrder.InviterId.index].substringAfter("=")
                        var channelUrl = friend[eventDataOrder.ChannelUrl.index].substringAfter("=")
                        var video = friend[eventDataOrder.Video.index].substringAfter("=")

                        MyEventArrayList.add(eventData(EventID, Invitee_No, Date, name, StartTime, EndTime, InviterId, FirstInviterName,
                                SecondInviterName, PlaceId, Image, description, channelUrl, video)
                        )
                    }
                }
                rv_eventsUploadViedo.layoutManager = LinearLayoutManager(this)
                rv_eventsUploadViedo.adapter =MyEventArrayListAdapter
            }
        }
    }





}
