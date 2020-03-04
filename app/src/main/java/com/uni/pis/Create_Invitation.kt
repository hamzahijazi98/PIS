package com.uni.pis

import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_create__invitation.*
import java.util.*
@RequiresApi(Build.VERSION_CODES.N)
private var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
lateinit var stime:String
lateinit var etime:String
lateinit var TimeSet:String
class Create_Invitation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create__invitation)


        btn_startTime.setOnClickListener {
            TimeSet="start"
            setTime(TimeSet)
        }
        btn_endTime.setOnClickListener {
            TimeSet="end"
            setTime(TimeSet)        }
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