package com.uni.pis.profile

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.uni.pis.Events.mStorageRef
import com.uni.pis.R
import com.uni.pis.data.friendData
import kotlinx.android.synthetic.main.activity_public_page_profile.*


class PublicPageProfile : AppCompatActivity() {
    lateinit var  fdata:friendData

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_public_page_profile)

        val bundle = intent.getBundleExtra("Bundle")
        val frienddata = bundle.getParcelable<friendData>("friendData")
        if (frienddata != null) {
            fdata=frienddata
        }
        if (fdata.image!="") {
            mStorageRef =
                FirebaseStorage.getInstance().getReferenceFromUrl(frienddata!!.image)
            mStorageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
                val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                iv_profile.setImageBitmap(
                    Bitmap.createScaledBitmap(
                        bmp, iv_profile.width,
                        iv_profile.height, false
                    )
                )
            }.addOnFailureListener {
                // Handle any errors
            }
        }
        tv_fullName.text= frienddata!!.first_name+" "+frienddata.last_name
        tv_email.text= frienddata.email
        tv_city.text= frienddata.city
        tv_birthday.text=frienddata.birthdate

    }
}
