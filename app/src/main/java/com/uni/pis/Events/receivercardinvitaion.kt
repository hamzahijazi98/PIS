package com.uni.pis.Events

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.storage.FirebaseStorage
import com.uni.pis.R
import com.uni.pis.data.eventData
import kotlinx.android.synthetic.main.activity_receivercardinvitaion.*
import kotlinx.android.synthetic.main.cardview_event_viewer.view.*

class receivercardinvitaion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receivercardinvitaion)

        val bundle = intent.getBundleExtra("Bundle")
        val eventdata = bundle.getParcelable<eventData>("eventdata")


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
        tv_finviter.text= eventdata.firstinvitername
        tv_sinviter.text= eventdata.secondinvitername
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
            i.data = Uri.parse("geo:" + loc[0] + "," + loc[1] + "?z=17")
            startActivity(i)

        }


















    }
}
