package com.uni.pis

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.Adapter.UsersAdapter.Home_Item_Adapter
import com.uni.pis.Events.EvenstList
import com.uni.pis.Data.UserData.userData
import com.uni.pis.homefrags.MainActivity
import com.uni.pis.Data.LoginData.Home_Item
import com.uni.pis.homefrags.HelpActivity
import com.uni.pis.profile.FindFriend
import kotlinx.android.synthetic.main.fragment_home_.*
import java.util.*


class HomeFrag : Fragment(), AdapterView.OnItemClickListener,BackgroundWorker.MyCallback {
    private var HomeItemArrayList: ArrayList<Home_Item>?=null
    private var GridViewHome: GridView?=null
    private var HomeItemArrayListAdapter: Home_Item_Adapter?=null
    lateinit var mStorageRef: StorageReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        GridViewHome=Gridview_home
        HomeItemArrayList=ArrayList()
        HomeItemArrayList=SetDataList()
        HomeItemArrayListAdapter= Home_Item_Adapter(view.context, HomeItemArrayList!!)
        GridViewHome?.adapter=HomeItemArrayListAdapter
        GridViewHome?.onItemClickListener=this


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
                    Log.d("HomeFrag", "onError: " + e.message)
                } catch (e: NullPointerException) {
                    Log.d("HomeFrag", "onError: " + e.message)
                } catch (e: Exception) {
                    Log.d("HomeFrag", "onError: " + e.message)
                }

            }.addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) { Log.d("HomeFrag", "onError: " + e.message) }
        tv_homeName.text= userData.first_name+" "+ userData.last_name
    }

    private fun SetDataList():ArrayList<Home_Item>{
        val HomerArrayList:ArrayList<Home_Item> = ArrayList()
        HomerArrayList.add(Home_Item(R.drawable.notf, getString(R.string.notification)))
        HomerArrayList.add(Home_Item(R.drawable.event,  getString(R.string.Events_List)))
        HomerArrayList.add(Home_Item(R.drawable.prof,  getString(R.string.Profile)))
        HomerArrayList.add(Home_Item(R.drawable.search,  getString(R.string.Find_Friend)))
        HomerArrayList.add(Home_Item(R.drawable.help,  getString(R.string.Help)))
        return HomerArrayList

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        HomeItemArrayList!![position]
        when (position) {
            0 -> {
                val intent = Intent (context,Notification::class.java)
                startActivity(intent)
            }

            1-> {
                val intent = Intent (context, EvenstList::class.java)
                startActivity(intent)
            }


            2-> {

                (activity as MainActivity?)!!.trans(2)
            }

            3->{
                val intent = Intent (context, FindFriend::class.java)
                startActivity(intent)
            }

            4->{val intent = Intent (context, HelpActivity::class.java)
                startActivity(intent)}


        }
    }

    override fun onResult(result: String?) {

}


}
