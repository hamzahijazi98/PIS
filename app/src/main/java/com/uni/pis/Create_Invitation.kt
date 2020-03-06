package com.uni.pis

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create__invitation.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)

private var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
lateinit var stime:String
lateinit var etime:String
lateinit var TimeSet:String
lateinit var Finviter:String
lateinit var Sinviter:String
lateinit var eventdate:String



class Create_Invitation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create__invitation)

        et_Finviter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_Finviter.text.isEmpty() || et_Finviter.text.length > 25)
                    et_Finviter.error = "Empty field or Long name ..."
                else
                    Finviter = et_Finviter.text.toString()
            }

        })
        et_Sinviter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
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
                        else
                            Sinviter = et_Sinviter.text.toString()
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


}