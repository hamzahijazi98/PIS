package com.uni.pis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.uni.pis.Adapter.Home_Item_Adapter
import com.uni.pis.homefrags.MainActivity
import com.uni.pis.model.Home_Item
import com.uni.pis.profile.ProfilePagePersonalFrag
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_create__invitation.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main_.*
import java.util.ArrayList



class HomeFrag : Fragment(), AdapterView.OnItemClickListener {
    private var arrayList: ArrayList<Home_Item>?=null
    private var gridView: GridView?=null
    private var itemAdapters: Home_Item_Adapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v= inflater.inflate(R.layout.fragment_main_, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gridView=Gridview_home
        arrayList=ArrayList()
        arrayList=SetDataList()
        itemAdapters=Home_Item_Adapter(view.context,arrayList!!)
        gridView?.adapter=itemAdapters
        gridView?.onItemClickListener=this




    }

    private fun SetDataList():ArrayList<Home_Item>{
        var arraylist:ArrayList<Home_Item> = ArrayList()
        arraylist.add(Home_Item(R.drawable.ic_image_black_24dp,"Home"))
        arraylist.add(Home_Item(R.drawable.ic_launcher_foreground,"Create Event"))
        arraylist.add(Home_Item(R.drawable.ic_launcher_foreground,"Profile"))
        arraylist.add(Home_Item(R.drawable.ic_launcher_foreground,"Help"))
        return arraylist

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var items: Home_Item = arrayList!![position]


        when (position) {
            0 -> {
                val fm=fragmentManager
                var trans= fm?.beginTransaction()
                if (trans != null) {
                    trans.replace(R.id.FrameHome,ProfilePagePersonalFrag())
                    trans.addToBackStack(null)
                    trans.commit()

                }



            }

            1-> {
                Toast.makeText(context, items.name, Toast.LENGTH_LONG).show()

            }






        }
    }

}
