package com.uni.pis.homefrags

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.uni.pis.*
import com.uni.pis.data.acceptFriendData
import com.uni.pis.profile.ProfilePagePersonalFrag
import kotlinx.android.synthetic.main.activity_home.*

class MainActivity : AppCompatActivity(),
    BackgroundWorker.MyCallback {
    @RequiresApi(Build.VERSION_CODES.N)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        FirebaseMessaging.getInstance().subscribeToTopic("FriendRequest")
        if (intent.hasExtra("UserID")) {
            val userId = intent.extras!!.get("UserID") .toString()
            val frinedId = intent.extras!!.get("FriendID") .toString()
            val image = intent.extras!!.get("Image") .toString()
            val name = intent.extras!!.get("name") .toString()
            val intent=Intent(this,Notification::class.java)
            intent.putExtra("UserID", userId)
            intent.putExtra("FriendID", frinedId)
            intent.putExtra("Image", image)
            intent.putExtra("name", name)
        }
        val viewpage_apdapter= MyViewPagerAdapter(supportFragmentManager)
        viewpage_apdapter.addfragment(HomeFrag(),"Home")
        viewpage_apdapter.addfragment(Events_Frag(),"Events Type")
        viewpage_apdapter.addfragment(ProfilePagePersonalFrag(),"Profile")
        view_pager.adapter=viewpage_apdapter
        tabs.setupWithViewPager(view_pager)
        try {
            var mFirebaseAuth = FirebaseAuth.getInstance()

            var data = BackgroundWorker(this)
            data.execute("login", mFirebaseAuth.currentUser?.uid!!)

        }
        catch (e:NullPointerException) {
            Toast.makeText(this,e.message, Toast.LENGTH_LONG).show()
        }



    }
    class MyViewPagerAdapter(manger: FragmentManager): FragmentPagerAdapter(manger){
        private val fragmentlist:MutableList<Fragment> =ArrayList()
        private val titlelist:MutableList<String> =ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentlist[position]
        }

        override fun getCount(): Int {
            return fragmentlist.size
        }
        fun addfragment(fragment: Fragment, title:String){
            fragmentlist.add(fragment)
            titlelist.add(title)

        }
        override fun getPageTitle(position: Int): CharSequence? {
            return titlelist[position]
        }
    }

    override fun onResult(result: String?) {

    }

}
