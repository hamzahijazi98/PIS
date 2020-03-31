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
import com.uni.pis.BackgroundWorker
import com.uni.pis.R
import kotlinx.android.synthetic.main.activity_create__invitation.*
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.N)

private var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
lateinit var stime:String
lateinit var etime:String
lateinit var TimeSet:String
lateinit var Finviter:String
lateinit var Sinviter:String
lateinit var eventdate:String
lateinit var LocationID:String
lateinit var Description:String
lateinit var mImageUri: Uri
lateinit var UserID:String
lateinit var imageStoragelink: String
var mFirebaseAuth = FirebaseAuth.getInstance()
lateinit var mStorageRef: StorageReference
val MAPS_CODE=1234
 var halls:ArrayList<String> = ArrayList()

    //image pick code
    val IMAGE_PICK_CODE = 1000;
    //Permission code
    private val PERMISSION_CODE = 1001;

class Create_Invitation : AppCompatActivity(),
    BackgroundWorker.MyCallback {
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
            date.show()

        }

        pick_img.setOnClickListener{
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions,
                        PERMISSION_CODE
                    );
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }



        btn_Save.setOnClickListener {
            Description =et_description.text.toString()
            UserID =
                mFirebaseAuth.currentUser?.uid!!
            uploadFile()
        }





        var myadap=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
            halls
        )
        lv_halls.adapter=myadap
        myadap.add("yousef")
        myadap.add("ha")
        myadap.add("auy")
        myadap.add("2313")
        myadap.add("ha")
        myadap.add("yoktob")
        myadap.add("ha")
        myadap.add("ha")
        myadap.add("ha")
        btn_maps.setOnClickListener {
            var i= Intent (this,  MapsActivity::class.java)
            startActivityForResult (i, MAPS_CODE)

        }

    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
fun setTime(set:String){
        val cal= Calendar.getInstance()
        val timePicker = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay)
                cal.set(Calendar.MINUTE,minute)
                if(set.equals("start")){
                    tv_startTime.text = timeFormat.format(cal.time)
                    stime = timeFormat.format(cal.time)}
                if(set.equals("end")){
                    tv_endTime.text = timeFormat.format(cal.time)
                    etime = timeFormat.format(cal.time)}

            }, cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),false)
        timePicker.show()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode)
        {
            MAPS_CODE ->  when (resultCode)
            {
                Activity.RESULT_OK -> {
                    LocationID = data?.extras?.get("location").toString()
                    if (!LocationID.isEmpty())
                ic_correct.visibility=View.VISIBLE}
                Activity.RESULT_CANCELED -> Toast.makeText(this ,"Nothing selected",Toast.LENGTH_LONG).show()
            }

            IMAGE_PICK_CODE ->{
                if (resultCode == Activity.RESULT_OK){
                    mImageUri = data!!.data!!
                    pick_img.setImageURI(mImageUri)
                }

            }
        }








  }
    override fun onResult(result: String?) {
        Toast.makeText(this,result,Toast.LENGTH_LONG).show()
    }
    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    private fun uploadFile() {
        if (mImageUri != null)
        {
            var fileReference = mStorageRef.child(
                UserID +System.currentTimeMillis().toString() + "." + getFileExtension(
                    mImageUri
                ))
            var uploadTask= fileReference.putFile(mImageUri)
                .addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(this, "Upload successful", Toast.LENGTH_LONG)
                        .show()
                    taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                        imageStoragelink =it.toString()
                        var data = BackgroundWorker(this)
                        data.execute("createEvent",
                            stime,
                            etime,
                            Finviter,
                            Sinviter,
                            eventdate,
                            LocationID,
                            Description,System.currentTimeMillis().toString(),
                            "wedding","150",
                            mFirebaseAuth.currentUser!!.uid,
                            imageStoragelink
                        )

                    }


                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
        }
        else
        {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }

}
