package com.uni.pis

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.data.userData
import kotlinx.android.synthetic.main.fragment_profile_page_personal.*


class ProfilePagePersonalFrag : Fragment() {
    lateinit var mStorageRef: StorageReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_page_personal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv_fullName.text= userData.first_name+ userData.last_name
        tv_city.text= userData.city
        tv_email.text= userData.email
        tv_gender.text= userData.gender
        tv_phone.text= userData.phoneNumber
        tv_birthday.text= userData.birthdate
        var image= userData.image.replace("\\","").trim()
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(image)
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


}
