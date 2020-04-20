//package com.uni.pis
//
//import android.app.DatePickerDialog
//import android.app.TimePickerDialog
//import android.icu.util.Calendar
//import android.net.Uri
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.StorageReference
//import com.google.firebase.storage.StorageTask
//import com.uni.pis.Data.UserData.UserDataGoogle
//import com.uni.pis.Events.*
//import kotlinx.android.synthetic.main.activity_add_halls.*
//import kotlinx.android.synthetic.main.activity_create__invitation.*
//import kotlinx.android.synthetic.main.activity_create__invitation.tv_date
//import kotlinx.android.synthetic.main.activity_sign_up.*
//
//class AddHalls : AppCompatActivity(), BackgroundWorker.MyCallback  {
//    private val IMAGE_PICK_CODE = 1000
//    var mFirebaseAuth = FirebaseAuth.getInstance()
//    lateinit var mStorageRef: StorageReference
//
//    var et_capacity: String=""
//    var et_noEnvitee: String=""
//    var et_hallno: String=""
//    var tv_availabledate: String=""
//    var tv_availabletime: String=""
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_halls)
//        com.uni.pis.Events.mStorageRef = FirebaseStorage.getInstance().getReference("eventPic");
//
//
//      et_capacity.addTextChangedListener(object : TextWatcher { ///////////sho a7ot hooon??//////
//        override fun afterTextChanged(s: Editable?) {
//            et_capacity = et_capacity.toString()
//        }
//
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        }
//
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            if (et_capacity.isEmpty())
//                et_capacity = "Empty field ..."
//
//
//        }
//
//    })
//        //////// envitee number////
//        et_noEnvitee(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                et_noEnvitee = et_noEnvitee.toString()
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (et_noEnvitee.isEmpty())
//                    et_noEnvitee = "Empty field ..."
//
//
//            }
//
//        })
//
//////// halls ID////
//        et_hallno.forEachIndexed(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                et_hallno = et_hallno.toString()
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (et_hallno.isEmpty())
//                    et_hallno = "Empty field ..."
//            }
//
//        })
//
//        //////available date of halls/////
//
//
//        btn_availabledate.setOnClickListener {
//            val now = Calendar.getInstance()
//            val date =
//                DatePickerDialog(
//                    this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
//                        tv_availabledate =
//                            dayOfMonth.toString() + "-" + (month + 1).toString() + "-" + year.toString()
//                        tv_availabledate = tv_availabledate
//                    },
//                    now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
//                )
//            date.show()
//
//        }
//
//
//
//
/////////////////// available time /////////
//
//
//
//        btn_availabletime.setOnClickListener {
//
//
//            val cal = Calendar.getInstance()
//            val timePicker = TimePickerDialog(this,
//                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
//                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
//                    cal.set(Calendar.MINUTE, minute)
//
//                    tv_availabletime = timeFormat.format(cal.time)
//                    tv_availabletime = timeFormat.format(cal.time)
//
//
//                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false
//            )
//            timePicker.show()
//        }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_halls)
//    }
//
//
//}
//
//    override fun onResult(result: String?) {
//           }
//
//    //?????????????????? private fun String.forEachIndexed(action: TextWatcher) {
//
//}
