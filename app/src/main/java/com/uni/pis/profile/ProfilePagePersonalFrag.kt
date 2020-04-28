package com.uni.pis.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.BackgroundWorker
import com.uni.pis.Data.UserData.userData
import com.uni.pis.Events.EvenstList
import com.uni.pis.R
import kotlinx.android.synthetic.main.fragment_profile_page_personal.*
import kotlin.system.exitProcess


class ProfilePagePersonalFrag : Fragment(),BackgroundWorker.MyCallback {
    var mFirebaseAuth = FirebaseAuth.getInstance()
    lateinit var mStorageRef: StorageReference
   lateinit var mGoogleSignInClient : GoogleSignIn
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(    R.layout.fragment_profile_page_personal, container, false)
    }

    @SuppressLint("ResourceType")
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
                    Log.d("ProfilePagePersonalFrag", "onError: " + e.message)
                } catch (e: NullPointerException) {
                    Log.d("ProfilePagePersonalFrag", "onError: " + e.message)
                } catch (e: Exception) {
                    Log.d("ProfilePagePersonalFrag", "onError: " + e.message)
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
        btn_myEvents.setOnClickListener {
            val intent=Intent(context, EvenstList::class.java)
            startActivity(intent)
        }
        btn_editProfile.setOnClickListener {
            activity?.let {
                val intent = Intent(it, EditProfileActivity::class.java)
                it.startActivity(intent)
            }
        }
        ib_logout.setOnClickListener {

            val builder = AlertDialog.Builder(view.context)
            builder.setMessage("Do you want to LogOut?")
            builder.setPositiveButton("Confirm"){ _, _ ->
                run {
                   try{
                       LoginManager.getInstance().logOut()
                   }
                   catch (e:Exception){Log.d("Logout",e.message)}
                    mFirebaseAuth.signOut()
                    context!!.deleteSharedPreferences("myPrefs")
                        exitProcess(0)



                }
            }
            builder.setNegativeButton("Cancel"){ _, _ ->
                Toast.makeText(view.context,"Cancelled.",Toast.LENGTH_SHORT).show()
            }
            builder.create().show()

        }


    }


    override fun onResult(result: String?) {

    }

}
