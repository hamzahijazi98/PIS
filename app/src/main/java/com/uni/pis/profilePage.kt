package com.uni.pis

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.data.userData
import kotlinx.android.synthetic.main.activity_profile_page.*
import kotlinx.android.synthetic.main.activity_profile_page.tv_gender as tv_gender1


class profilePage : AppCompatActivity(), BackgroundWorker.MyCallback {
    var firstName: String? = ""
    var lastName: String? = ""
    var email: String? = ""
    var birth_day: String? = ""
    var birth_month: String? = ""
    var birth_year: String? = ""
    var phone: String? = ""
    var gender: String? = ""
    var city: String? = ""
    lateinit var mStorageRef: StorageReference
    var image: String? = ""

    override fun onResult(result: String?) {

        tv_fullName.text=userData.first_name+userData.last_name
        tv_city.text=userData.city
        tv_email.text=userData.email
        tv_gender1.text=userData.gender
        tv_phone.text=userData.phoneNumber
        tv_birthday.text= userData.birthdate
        mStorageRef = FirebaseStorage.getInstance().getReference().child("uploads").child("hamzahijazi1583221202115.jpg")


        mStorageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            imageView2.setImageBitmap(
                Bitmap.createScaledBitmap(
                    bmp, imageView2.width,
                    imageView2.height, false
                )
            )
        }.addOnFailureListener {
            // Handle any errors
        }




    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        try {
            var mFirebaseAuth = FirebaseAuth.getInstance()

            var data = BackgroundWorker(this)
            data.execute("login", mFirebaseAuth.currentUser?.uid!!)



        }
        catch (e:NullPointerException)
        {
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }


    }




}
