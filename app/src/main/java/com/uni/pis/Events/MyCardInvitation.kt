package com.uni.pis.Events

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.google.firebase.storage.FirebaseStorage
import com.uni.pis.R
import com.uni.pis.data.eventData
import com.uni.pis.profile.Friends
import kotlinx.android.synthetic.main.activity_mycardinvitation.*

class MyCardInvitation : AppCompatActivity() {
    lateinit var  evetD:eventData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mycardinvitation)

        val bundle = intent.getBundleExtra("Bundle")
        val eventdata = bundle.getParcelable<eventData>("eventdata")
        if (eventdata != null) {
            evetD=eventdata
        }
        if (eventdata!!.image!="") {
            mStorageRef =
                FirebaseStorage.getInstance().getReferenceFromUrl(eventdata.image)
            mStorageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
                val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                event_img.setImageBitmap(
                    Bitmap.createScaledBitmap(
                        bmp, event_img.width,
                        event_img.height, false
                    )
                )
            }.addOnFailureListener {
                // Handle any errors
            }
        }
        tv_finviter.text= eventdata!!.firstinvitername
        tv_sinviter.text= eventdata!!.secondinvitername
        tv_eventdate.text= eventdata.Date
        tv_starttime.text= eventdata.StartTime
        tv_endtime.text= eventdata.EndTime
        tv_description.text= eventdata.Description
        btn_location.setOnClickListener {
            // lat= 32.0233
            // lon= 35.8759
            var loc=eventdata.Place_ID.split('&')
            var i = Intent()
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse("google.navigation:q=" + loc[0] + "," + loc[1])
            startActivity(i)
        }
        btn_invite.setOnClickListener {
            var intent=Intent(this,Friends::class.java)
            intent.putExtra("EventId",eventdata.Event_ID)
            intent.putExtra("InviteeNumber",eventdata.Inv_No)
            startActivity(intent)
        }
        btn_groupchat.setOnClickListener {
            var intent=Intent(this,Chat::class.java)
            intent.putExtra("ChannelUrl",eventdata.channelUrl)
            startActivity(intent)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId


        if (id == R.id.ic_edit) {
            val image = evetD
            val i = Intent(this, EditEvent::class.java)
            val bundle = Bundle()
            val parcel = image
            bundle.putParcelable("eventdata", parcel)
            i.putExtra("Bundle", bundle)
            ContextCompat.startActivity(this, i, Bundle())
        }
        return super.onOptionsItemSelected(item)
    }



}





