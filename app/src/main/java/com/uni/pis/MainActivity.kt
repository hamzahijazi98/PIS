package com.uni.pis

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BackgroundWorker.MyCallback {
    @RequiresApi(Build.VERSION_CODES.N)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewpage_apdapter=MyViewPagerAdapter(supportFragmentManager)
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
        catch (e:NullPointerException)
        {
            Toast.makeText(this,e.message, Toast.LENGTH_LONG).show()
        }


    }
    class MyViewPagerAdapter(manger: FragmentManager): FragmentPagerAdapter(manger){
        private val fragmentlist:MutableList<Fragment> =ArrayList()
        private val titlelist:MutableList<String> =ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentlist[position]       }

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
