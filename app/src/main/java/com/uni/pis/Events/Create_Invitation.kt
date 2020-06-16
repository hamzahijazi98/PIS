package com.uni.pis.Events

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sendbird.android.GroupChannel
import com.sendbird.android.SendBird
import com.sendbird.android.SendBirdException
import com.sendbird.android.User
import com.uni.pis.BackgroundWorker
import com.uni.pis.R
import kotlinx.android.synthetic.main.activity_create__invitation.*
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.N)
var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
lateinit var stime: String
lateinit var etime: String
lateinit var TimeSet: String
lateinit var Finviter: String
lateinit var Sinviter: String
lateinit var eventdate: String
lateinit var LocationID: String
lateinit var Description: String
lateinit var mImageUri: Uri
lateinit var UserID: String
lateinit var imageStoragelink: String
var mFirebaseAuth = FirebaseAuth.getInstance()
lateinit var mStorageRef: StorageReference
val MAPS_CODE = 1234
var halls: ArrayList<String> = ArrayList()


val IMAGE_PICK_CODE = 1000;
private val PERMISSION_CODE = 1001;

class Create_Invitation : AppCompatActivity(),
    BackgroundWorker.MyCallback {
    private val SENDBIRDAPPID = "74BB1D8B-0F07-402B-ABD1-7A20E5B7E7AE"
    private var CHANNEL_URL = ""
    private val gc: GroupChannel? = null
    private val userid = mFirebaseAuth.currentUser?.uid!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create__invitation)
        mStorageRef = FirebaseStorage.getInstance().getReference("eventPic");

        et_Finviter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Finviter = et_Finviter.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_Finviter.text.isEmpty() || et_Finviter.text.length > 25)
                    et_Finviter.error = "Empty field or Long name ..."


            }

        })
        et_Sinviter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Sinviter = et_Sinviter.text.toString()

            }


            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                et_Finviter.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (et_Sinviter.text.isEmpty() || et_Sinviter.text.length > 25)
                            et_Sinviter.error = "Empty field or Long name ..."

                    }

                }
                );
            }

        })
        btn_startTime.setOnClickListener {
            TimeSet = "start"
            setTime(TimeSet)
        }
        btn_endTime.setOnClickListener {
            TimeSet = "end"
            setTime(TimeSet)
        }
        btn_eventDate.setOnClickListener {
            val now = Calendar.getInstance()
            val date =
                DatePickerDialog(
                    this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        eventdate =
                            dayOfMonth.toString() + "-" + (month + 1).toString() + "-" + year.toString()
                        tv_date.text = eventdate
                    },
                    now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
                )
            date.datePicker.minDate = System.currentTimeMillis()
            date.show()

        }
        pick_img.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(
                        permissions,
                        PERMISSION_CODE
                    );
                } else {
                    //permission already granted
                    pickImageFromGallery();
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }


        btn_Save.setOnClickListener {
            if (mImageUri != null) {
                Description = et_description.text.toString()
                UserID =
                    mFirebaseAuth.currentUser?.uid!!
                val users = java.util.ArrayList<String>()
                users.add(userid)

                GroupChannel.createChannelWithUserIds(users, true) { groupChannel, e ->
                    CHANNEL_URL = groupChannel.url
                    uploadFile()
                }
            }

        }
        SendBird.init(SENDBIRDAPPID, this)
        SendBird.connect(userid, object : SendBird.ConnectHandler {
            override fun onConnected(user: User?, e: SendBirdException?) {
                if (e != null) {
                    return
                }
            }
        })


        // Accepting an invitation
        SendBird.setChannelInvitationPreference(true, object : SendBird.SetChannelInvitationPreferenceHandler {
            override fun onResult(e: SendBirdException?) {
                if (e != null) { // Error.
                    return
                }
            }
        })
        val myadap = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, halls)
        lv_halls.adapter = myadap
        myadap.add("Future Update")

        btn_maps.setOnClickListener {
            val i = Intent(this, MapsActivity::class.java)
            startActivityForResult(i, MAPS_CODE)

        }
    }
    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun setTime(set: String) {
        val cal = Calendar.getInstance()
        val timePicker = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                if (set.equals("start")) {
                    tv_startTime.text = timeFormat.format(cal.time)
                    stime = timeFormat.format(cal.time)
                }
                if (set.equals("end")) {
                    tv_endTime.text = timeFormat.format(cal.time)
                    etime = timeFormat.format(cal.time)
                }

            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false
        )
        timePicker.show()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            MAPS_CODE -> when (resultCode) {
                Activity.RESULT_OK -> {
                    LocationID = data?.extras?.get("location").toString()
                    if (!LocationID.isEmpty())
                        ic_correct.visibility = View.VISIBLE
                }
                Activity.RESULT_CANCELED -> Toast.makeText(this, "Nothing selected", Toast.LENGTH_LONG).show()
            }

            IMAGE_PICK_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    mImageUri = data!!.data!!
                    pick_img.setImageURI(mImageUri)
                }

            }
        }
    }
    override fun onResult(result: String?) {
        Toast.makeText(this, "Event Created Successfully", Toast.LENGTH_LONG).show()
        val intent = Intent(this, EvenstList::class.java)
        startActivity(intent)
    }
    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }
    private fun uploadFile() {
        if (mImageUri != null) {
            var fileReference = mStorageRef.child(
                UserID + System.currentTimeMillis().toString() + "." + getFileExtension(
                    mImageUri
                )
            )
            var uploadTask = fileReference.putFile(mImageUri)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                        imageStoragelink = it.toString()
                        var data = BackgroundWorker(this)
                        data.execute(
                            "createEvent",
                            stime,
                            etime,
                            Finviter,
                            Sinviter,
                            eventdate,
                            LocationID,
                            Description, System.currentTimeMillis().toString(),
                            "wedding", "150",
                            mFirebaseAuth.currentUser!!.uid,
                            imageStoragelink,
                            CHANNEL_URL
                        )
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }

}
