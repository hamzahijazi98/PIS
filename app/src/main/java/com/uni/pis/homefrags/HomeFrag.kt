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
import com.uni.pis.Adapter.Home_Item_Adapter
import com.uni.pis.Events.EvenstList
import com.uni.pis.data.userData
import com.uni.pis.homefrags.MainActivity
import com.uni.pis.model.Home_Item
import com.uni.pis.profile.FindFriend
import kotlinx.android.synthetic.main.fragment_home_.*
import java.util.*


class HomeFrag : Fragment(), AdapterView.OnItemClickListener,BackgroundWorker.MyCallback {
    private var arrayList: ArrayList<Home_Item>?=null
    private var gridView: GridView?=null
    private var itemAdapters: Home_Item_Adapter?=null
    lateinit var mStorageRef: StorageReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gridView=Gridview_home
        arrayList=ArrayList()
        arrayList=SetDataList()
        itemAdapters=Home_Item_Adapter(view.context,arrayList!!)
        gridView?.adapter=itemAdapters
        gridView?.onItemClickListener=this


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
        } catch (e: Exception) { Toast.makeText(context, e.message, Toast.LENGTH_LONG).show() }
        tv_homeName.text=userData.first_name+" "+userData.last_name
    }

    private fun SetDataList():ArrayList<Home_Item>{
        var arraylist:ArrayList<Home_Item> = ArrayList()
        arraylist.add(Home_Item(R.drawable.notf,"Notification"))
        arraylist.add(Home_Item(R.drawable.event,"Events List"))
        arraylist.add(Home_Item(R.drawable.prof,"Profile"))
        arraylist.add(Home_Item(R.drawable.search,"Find Friend"))
        arraylist.add(Home_Item(R.drawable.help,"Help"))
        return arraylist

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var items: Home_Item = arrayList!![position]
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



        }
    }

    override fun onResult(result: String?) {

}


}
