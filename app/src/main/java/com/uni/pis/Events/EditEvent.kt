package com.uni.pis.Events

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.BackgroundWorker
import com.uni.pis.R
import com.uni.pis.Data.EventData.eventData
import kotlinx.android.synthetic.main.activity_edit_event.*
import kotlinx.android.synthetic.main.activity_edit_event.btn_eventDate
import kotlinx.android.synthetic.main.activity_edit_event.btn_maps
import kotlinx.android.synthetic.main.activity_edit_event.pick_img
import kotlinx.android.synthetic.main.activity_edit_event.tv_date
import java.util.*

class EditEvent : AppCompatActivity(),BackgroundWorker.MyCallback{
    private var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)

    var mFirebaseAuth = FirebaseAuth.getInstance()
    lateinit var mStorageRef: StorageReference
    lateinit var mDatabaseRef: DatabaseReference
    lateinit var imageStoragelink: String
    var ImageUri: Uri ?= null
    var timeSet: String = ""
    lateinit var eventName: String
    lateinit var finviter: String

    lateinit var LocationId: String

    lateinit var sinviter: String
    lateinit var date: String
    lateinit var stime: String
    lateinit var etime: String
    lateinit var descrip: String
    lateinit var image:String
    lateinit var UserID: String
    lateinit var EventID:String
    lateinit var eventdata: eventData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)
        mStorageRef = FirebaseStorage.getInstance().getReference("eventPic");

        val bundle = intent.getBundleExtra("Bundle")
         eventdata = bundle.getParcelable<eventData>("eventdata")!!


         eventName = eventdata!!.Event_type
         finviter = eventdata.firstinvitername
         sinviter = eventdata.secondinvitername
         date = eventdata.Date
         stime = eventdata.StartTime
         etime = eventdata.EndTime
         descrip = eventdata.Description
        LocationId= eventdata.Place_ID
         image = eventdata.image
         UserID = eventdata.InviterID
         EventID=eventdata.Event_ID


        pick_img.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }
        et_finviter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                finviter = s.toString()
                eventdata.firstinvitername = finviter
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        et_sinviter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                sinviter = s.toString()
                eventdata.secondinvitername = s.toString()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        btn_eventDate.setOnClickListener {
            val now = Calendar.getInstance()
            val date =
                DatePickerDialog(
                    this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        date =
                            dayOfMonth.toString() + "-" + (month + 1).toString() + "-" + year.toString()
                        eventdata.Date = date
                        tv_date.text = eventdata.Date
                    },
                    now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
                )
            date.show()

        }
        btn_starttime.setOnClickListener {
            timeSet = "start"
            setTime(timeSet)
        }
        btn_endtime.setOnClickListener {
            timeSet = "end"
            setTime(timeSet)
        }
        et_descrip.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                descrip = s.toString()
                eventdata.Description = descrip
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        et_finviter.setText(eventdata.firstinvitername)
        et_sinviter.setText(eventdata.secondinvitername)
        et_descrip.setText(eventdata.Description)
        btn_SAVE.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Edit Confirmation")
            builder.setPositiveButton("Confirm") { _, _ ->
                run {
                    try {
                        UserID = mFirebaseAuth.currentUser?.uid!!
                        uploadFile()
                    } catch (e: NullPointerException) {
                        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    }


                }
            }
            builder.setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(this, "Cancelled.", Toast.LENGTH_SHORT).show()
            }
            builder.create().show()

        }
        btn_maps.setOnClickListener {
            var i= Intent (this,  MapsActivity::class.java)
            startActivityForResult (i, com.uni.pis.Events.MAPS_CODE)

        }

}
    fun setTime(set:String){
        val cal= Calendar.getInstance()
        val timePicker = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay)
                cal.set(Calendar.MINUTE,minute)
                if(set == "start"){
                    tv_starttime.text = timeFormat.format(cal.time)
                    stime=timeFormat.format(cal.time)
                    eventdata.StartTime = timeFormat.format(cal.time)}
                if(set == "end"){
                    tv_endtime.text = timeFormat.format(cal.time)
                    etime=timeFormat.format(cal.time)
                    eventdata.EndTime = timeFormat.format(cal.time)}

            }, cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),false)
        timePicker.show()

    }
    private val IMAGE_PICK_CODE = 1000
    val MAPS_CODE=1234
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            super.onActivityResult(requestCode, resultCode, data)
            when (requestCode) {
                IMAGE_PICK_CODE -> {
                    if (resultCode == Activity.RESULT_OK) {
                        ImageUri = data!!.data
                        pick_img.setImageURI(ImageUri)
                    }
                }
                MAPS_CODE ->  when (resultCode) {
                    Activity.RESULT_OK -> {
                        LocationId= data?.extras?.get("location").toString()
                        if (LocationId.isNotEmpty())
                            ic_Correct.visibility= View.VISIBLE
                        eventdata.Place_ID=LocationId
                    }
                    Activity.RESULT_CANCELED -> Toast.makeText(this ,"Nothing selected", Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }
    private fun uploadFile() {
        if (ImageUri != null)
        {
            var fileReference = mStorageRef.child(
                UserID +System.currentTimeMillis().toString() + "." + getFileExtension(
                    ImageUri!!
                ))
            var uploadTask= fileReference.putFile(ImageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(this, "Upload successful", Toast.LENGTH_LONG)
                        .show()
                    taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                        image=it.toString()
                        eventdata.image=image

                        var data = BackgroundWorker(this)
                        data.execute("updatevent", stime, etime, finviter, sinviter, date, LocationId, descrip, "150", image,EventID)
                    }


                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
        }
        else
        {
            var data = BackgroundWorker(this)
            data.execute("updatevent", stime, etime, finviter, sinviter, date, LocationId, descrip, "150", image,EventID)
        }
    }

    override fun onResult(result: String?) {

    }


}


