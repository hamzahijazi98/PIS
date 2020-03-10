package com.uni.pis

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
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
var mFirebaseAuth = FirebaseAuth.getInstance()

val MAPS_CODE=1234
 var halls:ArrayList<String> = ArrayList()

class Create_Invitation : AppCompatActivity(),BackgroundWorker.MyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create__invitation)

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
        btn_Save.setOnClickListener {
            Description=et_description.text.toString()

           var data = BackgroundWorker(this)
           data.execute("createEvent", stime, etime, Finviter, Sinviter, eventdate, LocationID,Description,System.currentTimeMillis().toString(),"wedding","150",
               mFirebaseAuth.currentUser!!.uid)
        }




        var myadap=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,halls)
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


fun setTime(set:String){
        val cal= Calendar.getInstance()
        val timePicker = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay)
                cal.set(Calendar.MINUTE,minute)
                if(set.equals("start")){
                    tv_startTime.text = timeFormat.format(cal.time)
                    stime= timeFormat.format(cal.time)}
                if(set.equals("end")){
                    tv_endTime.text = timeFormat.format(cal.time)
                    etime= timeFormat.format(cal.time)}

            }, cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),false)
        timePicker.show()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode)
        {
            MAPS_CODE ->  when (resultCode)
            {

                Activity.RESULT_OK -> {LocationID= data?.extras?.get("location").toString()
                    if (!LocationID.isEmpty())
                ic_correct.visibility=View.VISIBLE}
                Activity.RESULT_CANCELED -> Toast.makeText(this ,"Nothing selected",Toast.LENGTH_LONG).show()
            }


        }




    }
    override fun onResult(result: String?) {
        Toast.makeText(this,result,Toast.LENGTH_LONG).show()
    }


}
