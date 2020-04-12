package com.uni.pis.homefrags

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sendbird.android.SendBird
import com.sendbird.android.SendBirdException
import com.sendbird.android.User
import com.uni.pis.BackgroundWorker
import com.uni.pis.Events_Frag
import com.uni.pis.HomeFrag
import com.uni.pis.R
import com.uni.pis.data.userData
import com.uni.pis.profile.ProfilePagePersonalFrag
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home_.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(),
    BackgroundWorker.MyCallback {
    @RequiresApi(Build.VERSION_CODES.N)
    private val SENDBIRDAPPID="C70ACBE6-0911-45D5-B02B-C56D3ADDF158"

    var mFirebaseAuth = FirebaseAuth.getInstance()
    lateinit var mStorageRef: StorageReference
    val viewpage_apdapter= MyViewPagerAdapter(supportFragmentManager)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var userid=mFirebaseAuth.currentUser?.uid!!
        FirebaseMessaging.getInstance().subscribeToTopic("FriendRequest$userid")
        SendBird.init(SENDBIRDAPPID, this)

        SendBird.connect(userid, object : SendBird.ConnectHandler { override fun onConnected(user: User?, e: SendBirdException?) {
            if (e != null)
            {
                return
            }
        } })
        SendBird.setChannelInvitationPreference(true, object : SendBird.SetChannelInvitationPreferenceHandler {
            override fun onResult(e: SendBirdException?) {
                if (e != null) { // Error.
                    return
                }
            }
        })


        viewpage_apdapter.addfragment(HomeFrag(),"Home")
        viewpage_apdapter.addfragment(Events_Frag(),"Create Event")
        viewpage_apdapter.addfragment(ProfilePagePersonalFrag(),"Profile")
        view_pager.adapter=viewpage_apdapter
        tabs.setupWithViewPager(view_pager)
        try {
            var mFirebaseAuth = FirebaseAuth.getInstance()

            var data = BackgroundWorker(this)
            data.execute("login", userid)
        }
        catch (e:NullPointerException) {
            Toast.makeText(this,e.message, Toast.LENGTH_LONG).show()
        }


    }



    @SuppressLint("SetTextI18n")
    override fun onResult(result: String?) {
        var image = userData.image.replace("\\", "").trim()
        try {
            mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(image)
            mStorageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
                val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                try {
                    home_iv_profile.setImageBitmap(
                        Bitmap.createScaledBitmap(
                            bmp, home_iv_profile.width,
                            home_iv_profile.height, false
                        )
                    )
                } catch (e: IllegalStateException) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                } catch (e: NullPointerException) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }

            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) { Toast.makeText(this, e.message, Toast.LENGTH_LONG).show() }
        tv_homeName.text=userData.first_name+"\n"+userData.last_name
    }

    override fun onBackPressed() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to close Eventor?")
        builder.setPositiveButton("Confirm"){ _, _ ->
            super.onBackPressed()
        }
        builder.setNegativeButton("Cancel"){ _, _ ->
            Toast.makeText(this,"Cancelled.",Toast.LENGTH_SHORT).show()
        }
        builder.create().show()
    }

    }


