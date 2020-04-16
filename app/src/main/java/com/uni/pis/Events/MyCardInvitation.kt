package com.uni.pis.Events

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.google.zxing.integration.android.IntentIntegrator
import com.uni.pis.BackgroundWorker
import com.uni.pis.R
import com.uni.pis.data.InviteeListData
import com.uni.pis.data.eventData
import com.uni.pis.profile.Friends
import kotlinx.android.synthetic.main.activity_invitee__list.*
import kotlinx.android.synthetic.main.activity_mycardinvitation.*

class MyCardInvitation : AppCompatActivity(),BackgroundWorker.MyCallback {
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

                var loc=eventdata.Place_ID.split('&')
                 var lat=loc[0].substringAfter("=")
                var lot= loc[1]
                var i = Intent()
                i.action = Intent.ACTION_VIEW
                i.data = Uri.parse("google.navigation:q=" + lat + "," + lot)
                startActivity(i)
            }

        btn_invite.setOnClickListener {
            var intent=Intent(this,Friends::class.java)
            intent.putExtra("EventId",eventdata.Event_ID)
            intent.putExtra("InviteeNumber",eventdata.Inv_No)
            intent.putExtra("channelUrl",eventdata.channelUrl)
            startActivity(intent)
        }

        btn_groupchat.setOnClickListener {
            var intent=Intent(this,Chat::class.java)
            intent.putExtra("ChannelUrl",eventdata.channelUrl)
            startActivity(intent)
        }
        btn_attendance.setOnClickListener {
            var intent=Intent(this,Invitee_List::class.java)
            intent.putExtra("eventID",eventdata.Event_ID)
            startActivity(intent)
        }
        btn_scan.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(false)
            scanner.initiateScan()
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    var data = BackgroundWorker(this)
                    data.execute("checkID",evetD.Event_ID ,result.contents)

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun onResult(result: String?) {



            var friend = result!!.split("!&")
            if (friend.size > 1) {

                var UserID = friend[Invitee_List.userDataOrder.UserID.index].substringAfter("=")
                var firstname =
                    friend[Invitee_List.userDataOrder.firstName.index].substringAfter("=")
                var lastname = friend[Invitee_List.userDataOrder.lastName.index].substringAfter("=")
                var image = friend[Invitee_List.userDataOrder.Image.index].substringAfter("=")
                    .replace("\\", "").trim()
                var attendence =
                    friend[Invitee_List.userDataOrder.attendace.index].substringAfter("=")
                var permission =
                    friend[Invitee_List.userDataOrder.permission.index].substringAfter("=")
                var inviteenumber =
                    friend[Invitee_List.userDataOrder.inviteenumber.index].substringAfter("=")
                InviteeListData(UserID,"$firstname  $lastname",image,attendence,permission,inviteenumber)

            img_complete.visibility=View.VISIBLE
                val handler = Handler()
                handler.postDelayed(
                    Runnable {img_complete.visibility=View.GONE
                    },
                    1000
                )



        }
        else{
                img_cancelled.visibility=View.VISIBLE
                val handler = Handler()
                handler.postDelayed(
                    Runnable {img_cancelled.visibility=View.GONE
                    },
                    1000
                )


            }





    }

}





