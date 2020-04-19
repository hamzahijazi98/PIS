package com.uni.pis

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.uni.pis.Data.UserData.UserDataGoogle

class AddHalls : AppCompatActivity(), BackgroundWorker.MyCallback  {
    private val IMAGE_PICK_CODE = 1000
    var mFirebaseAuth = FirebaseAuth.getInstance()
    lateinit var mStorageRef: StorageReference
    lateinit var mDatabaseRef: DatabaseReference
    private lateinit var mUploadTask: StorageTask<*>
    var et_capacity: String=""
    var et_noEnvitee: String=""
    var et_hallno: String=""
    var et_availabledate: String=""
    var et_availabletime: String=""







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_halls)
    }

    override fun onResult(result: String?) {

    }
}
