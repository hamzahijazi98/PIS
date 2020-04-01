package com.uni.pis.profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.R
import com.uni.pis.data.userData
import com.uni.pis.homefrags.Edit_Profile_Fragment
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
        tv_fullName.text = "${userData.first_name} ${userData.last_name}"
        tv_city.text = userData.city
        tv_email.text = userData.email
        tv_gender.text = userData.gender
        tv_phone.text = userData.phoneNumber
        tv_birthday.text = userData.birthdate

        var image = userData.image.replace("\\", "").trim()
        try {


            mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(image)
            mStorageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
                val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                try {


                    iv_profile.setImageBitmap(
                        Bitmap.createScaledBitmap(
                            bmp, iv_profile.width,
                            iv_profile.height, false
                        )
                    )
                } catch (e: IllegalStateException) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                } catch (e: NullPointerException) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }

            }.addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }



        btn_friends.setOnClickListener {
            val intent = Intent(context, Friends::class.java)
            startActivity(intent)

        }
        btn_editProfile.setOnClickListener {
            val fm = fragmentManager
            var trans = fm?.beginTransaction()
            if (trans != null) {
                trans.replace(R.id.fragment_profile_page_personal, Edit_Profile_Fragment())
                trans.addToBackStack(null)
                trans.commit()
            }
        }
    }

}
