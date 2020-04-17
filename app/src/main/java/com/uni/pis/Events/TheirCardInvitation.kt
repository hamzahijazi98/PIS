package com.uni.pis.Events

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.uni.pis.BackgroundWorker
import com.uni.pis.R
import com.uni.pis.Data.EventData.eventData
import kotlinx.android.synthetic.main.activity_their_cardinvitation.*

class TheirCardInvitation : AppCompatActivity(),BackgroundWorker.MyCallback {
    lateinit var  EventData: eventData
    var attendaceStatus="0"
    var mFirebaseAuth = FirebaseAuth.getInstance()
    var UserID= mFirebaseAuth.currentUser!!.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_their_cardinvitation)

        val bundle = intent.getBundleExtra("Bundle")
        val Eventdata = bundle.getParcelable<eventData>("eventdata")
        if (Eventdata != null) {
            EventData = Eventdata
        }
        if (Eventdata!!.image != "") {
            mStorageRef =
                FirebaseStorage.getInstance().getReferenceFromUrl(Eventdata.image)
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
        tv_finviter.text = Eventdata.firstinvitername
        tv_sinviter.text = Eventdata.secondinvitername
        tv_eventdate.text = Eventdata.Date
        tv_starttime.text = Eventdata.StartTime
        tv_endtime.text = Eventdata.EndTime
        tv_description.text = Eventdata.Description
        btn_location.setOnClickListener {
            var loc=Eventdata.Place_ID.split('&')
            var lat=loc[0].substringAfter("=")
            var lot= loc[1]
            var i = Intent()
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse("google.navigation:q=" + lat + "," + lot)
            startActivity(i)
        }
        /*    btn_invite.setOnClickListener {
            var intent= Intent(this, Friends::class.java)
            intent.putExtra("EventId",eventdata.Event_ID)
            intent.putExtra("InviteeNumber",eventdata.Inv_No)
            intent.putExtra("channelUrl",eventdata.channelUrl)
            startActivity(intent)
        }*/

        btn_groupchat.setOnClickListener {
            var intent = Intent(this, Chat::class.java)
            intent.putExtra("ChannelUrl", Eventdata.channelUrl)
            startActivity(intent)
        }

        btn_acceptInvite.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("You Are Accepting Invitation Are You Sure ? ")
            builder.setPositiveButton("Confirm") { _, _ ->
                try {
                    attendaceStatus = "2"
                    var data = BackgroundWorker(this)
                    data.execute("updateAttendanceStatus", UserID, EventData.Event_ID, attendaceStatus)
                } catch (e: Exception) {
                }
            }
            builder.setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(this, "Cancelled.", Toast.LENGTH_SHORT).show()
            }
            builder.create().show()

        }
        btn_reject.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("You Are Rejecting Invitation Are You Sure ? ")
            builder.setPositiveButton("Confirm") { _, _ ->
                try {
                    attendaceStatus = "0"
                    var data = BackgroundWorker(this)
                    data.execute("updateAttendanceStatus", UserID, EventData.Event_ID, attendaceStatus)
                } catch (e: Exception) {
                }
            }
            builder.setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(this, "Cancelled.", Toast.LENGTH_SHORT).show()
            }
            builder.create().show()

        }
        btn_notsure.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("You Are Holding Invitation Are You Sure ? ")
            builder.setPositiveButton("Confirm") { _, _ ->

                try {
                    attendaceStatus = "1"
                    var data = BackgroundWorker(this)
                    data.execute("updateAttendanceStatus", UserID, EventData.Event_ID, attendaceStatus)
                } catch (e: Exception) {
                }
            }
            builder.setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(this, "Cancelled.", Toast.LENGTH_SHORT).show()
            }
            builder.create().show()

            }
        btn_attendanceQR.setOnClickListener {
            var intent = Intent(this, QrCodeGenerate::class.java)
            intent.putExtra("userID",UserID )
            startActivity(intent)

        }

    }

    override fun onResult(result: String?) {

    }
}
